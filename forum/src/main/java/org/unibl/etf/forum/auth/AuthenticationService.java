package org.unibl.etf.forum.auth;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.config.JwtService;
import org.unibl.etf.forum.config.TokenBlacklist;
import org.unibl.etf.forum.exceptions.AccountNotActivatedException;
import org.unibl.etf.forum.exceptions.InvalidTwoFactorCodeException;
import org.unibl.etf.forum.exceptions.InvalidUsernameException;
import org.unibl.etf.forum.models.entities.UserEntity;
import org.unibl.etf.forum.repositories.UserRepository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final TokenBlacklist tokenBlacklist;
    private final Map<String, String> twoFactorCodeMap = new ConcurrentHashMap<>();

    public TempAuthResponse login(AuthRequest request) throws AccountNotActivatedException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidUsernameException("Invalid username or password."));

        if (user.getStatus()) {
            String twoFactorCode = generateTwoFactorCode();
            twoFactorCodeMap.put(user.getUsername(), twoFactorCode);
            sendTwoFactorCodeEmail(user.getEmail(), twoFactorCode);

            return new TempAuthResponse(user.getUsername());
        } else {
            throw new AccountNotActivatedException("Account not activated!");
        }
    }

    public JwtAuthResponse verifyTwoFactorCode(String username, String userSubmittedCode) {
        String storedCode = twoFactorCodeMap.get(username);
        if (storedCode != null && storedCode.equals(userSubmittedCode)) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new InvalidUsernameException("Invalid username."));
            var jwt = jwtService.generateToken(user);
            twoFactorCodeMap.remove(username); // Remove the code after successful verification
            return JwtAuthResponse.builder().token(jwt).build();
        } else {
            throw new InvalidTwoFactorCodeException("Invalid two-factor authentication code.");
        }
    }

    private void sendTwoFactorCodeEmail(String email, String twoFactorCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Two-Factor Authentication Code");
        message.setText("Your 2FA code is: " + twoFactorCode);
        mailSender.send(message);
    }

    public void sendActivationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your Account Is Activated");
        message.setText("Your account is activated!");
        mailSender.send(message);
    }

    private String generateTwoFactorCode() {
        // Generate a random 6-digit code
        return Integer.toString(new Random().nextInt(900000) + 100000);
    }

    public UserEntity signup(SignUpRequest signUpRequest) {
        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("ROLE_USER");
        user.setStatus(false);
        return userRepository.save(user);
    }

    public void logout(String token) {
        // Blacklist the provided JWT token
        tokenBlacklist.blacklistToken(token);
    }

    public TempAuthResponse githubLogin(String code) throws AccountNotActivatedException {
        String clientId = "f22d73ba4a4e441df84f";
        String clientSecret = "76828b87376523bc3f711a9b6f929efe9b291eef";
        String redirectUri = "http://localhost:4200/verification";

        try {
            // Construct the request URL
            URI uri = new URIBuilder("https://github.com/login/oauth/access_token")
                    .addParameter("client_id", clientId)
                    .addParameter("client_secret", clientSecret)
                    .addParameter("code", code)
                    .addParameter("redirect_uri", redirectUri)
                    .build();

            // Create an HTTP POST request
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost postRequest = new HttpPost(uri);
            postRequest.setEntity(new StringEntity("", ContentType.APPLICATION_FORM_URLENCODED));

            // Send the request and get the response
            HttpResponse response = client.execute(postRequest);

            // Get the response body as a string
            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);

            // Extract the access token from the response body
            String accessToken = extractAccessToken(responseBody);

            // Use the access token to fetch user details
            String userDetails = getUserDetails(accessToken);

            // Extract the login field value from the user details
            String email = extractEmail(userDetails);

            Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
                   if(optionalUser.isPresent()) {
                       UserEntity user = optionalUser.get();
                       if (user.getStatus()) {
                           String twoFactorCode = generateTwoFactorCode();
                           twoFactorCodeMap.put(user.getUsername(), twoFactorCode);
                           sendTwoFactorCodeEmail(user.getEmail(), twoFactorCode);

                           return new TempAuthResponse(user.getUsername());
                       } else {
                           throw new AccountNotActivatedException("Account not activated!");
                       }
                   }else{
                       // Save the new user
                       UserEntity newUser = new UserEntity();
                       newUser.setEmail(email);
                       newUser.setUsername(extractUsername(userDetails));
                       newUser.setPasswordHash("-- GitHub Account --");
                       newUser.setRole("ROLE_USER");
                       newUser.setStatus(false);
                       userRepository.save(newUser);
                   }
        } catch (Exception e) {
            throw new InvalidUsernameException("Invalid username or password.");
        }
        return null;
    }


    private String extractAccessToken(String responseBody) {
        String prefix = "access_token=";
        String suffix = "&scope";
        int startIndex = responseBody.indexOf(prefix);
        int endIndex = responseBody.indexOf(suffix);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return responseBody.substring(startIndex + prefix.length(), endIndex);
        }
        return null; // Return null if the access token cannot be extracted
    }

    private String getUserDetails(String accessToken) throws URISyntaxException, IOException {
        URI uri = new URIBuilder("https://api.github.com/user")
                .build();

        // Create an HTTP GET request
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet getRequest = new HttpGet(uri);
        getRequest.setHeader("Authorization", "Bearer " + accessToken);

        // Send the request and get the response
        HttpResponse response = client.execute(getRequest);

        // Get the response body as a string
        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);

        // Return the response body to the caller
        return responseBody;
    }

    private String extractEmail(String userDetails) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(userDetails, JsonObject.class);
        if (jsonObject.has("email")) {
            return jsonObject.get("email").getAsString();
        }
        return null; // Return null if the login field is not present
    }

    private String extractUsername(String userDetails) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(userDetails, JsonObject.class);
        if (jsonObject.has("login")) {
            return jsonObject.get("login").getAsString();
        }
        return null; // Return null if the login field is not present
    }


}
