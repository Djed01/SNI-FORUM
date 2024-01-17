package org.unibl.etf.forum.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.unibl.etf.forum.config.JwtService;
import org.unibl.etf.forum.exceptions.AccountNotActivatedException;
import org.unibl.etf.forum.exceptions.InvalidTwoFactorCodeException;
import org.unibl.etf.forum.exceptions.InvalidUsernameException;
import org.unibl.etf.forum.models.entities.UserEntity;
import org.unibl.etf.forum.repositories.UserRepository;

import java.util.Map;
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
    private final Map<String, String> twoFactorCodeMap = new ConcurrentHashMap<>();

    public TempAuthResponse login(AuthRequest request) throws AccountNotActivatedException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new InvalidUsernameException("Invalid username or password."));

        if(user.getStatus()) {
            String twoFactorCode = generateTwoFactorCode();
            twoFactorCodeMap.put(user.getUsername(), twoFactorCode);
            sendTwoFactorCodeEmail(user.getEmail(), twoFactorCode);

            return new TempAuthResponse(user.getUsername());
        }else{
            throw  new AccountNotActivatedException("Account not activated!");
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

    public void sendActivationEmail(String email){
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

    public UserEntity signup(SignUpRequest signUpRequest){
        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPasswordHash(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setRole("ROLE_USER");
        user.setStatus(false);
        return userRepository.save(user);
    }
}
