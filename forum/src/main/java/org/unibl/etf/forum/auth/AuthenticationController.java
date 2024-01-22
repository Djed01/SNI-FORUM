package org.unibl.etf.forum.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.exceptions.AccountNotActivatedException;
import org.unibl.etf.forum.exceptions.InvalidUsernameException;
import org.unibl.etf.forum.models.entities.UserEntity;

import java.io.IOException;
import java.net.URISyntaxException;


@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @CrossOrigin(origins = "https://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            TempAuthResponse response = authenticationService.login(request);
            return ResponseEntity.ok(response);
        } catch (AccountNotActivatedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Account not activated"));
        } catch (InvalidUsernameException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid username or password"));
        }
    }

    @CrossOrigin(origins = "https://localhost:4200")
    @PostMapping("/verify-2fa")
    public ResponseEntity<JwtAuthResponse> verifyTwoFactorCode(@RequestBody TwoFactorVerificationRequest request) {
        return ResponseEntity.ok(authenticationService.verifyTwoFactorCode(
                request.getUsername(), request.getUserSubmittedCode()));
    }

    @CrossOrigin(origins = "https://localhost:4200")
    @PostMapping("/signup")
    public ResponseEntity<UserEntity> signup(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }

    @CrossOrigin(origins = "https://localhost:4200")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody TokenDTO tokenDto) {
        // Blacklist the token
        String token = tokenDto.getToken();
        authenticationService.logout(token);
        return ResponseEntity.status(HttpStatus.OK).body("Logged out successfully");
    }

    @CrossOrigin(origins = "https://localhost:4200")
    @GetMapping("/github-token-endpoint")
    public ResponseEntity<?> githubLogin(@RequestParam String code) {
        try {
            TempAuthResponse response = authenticationService.githubLogin(code);
            return ResponseEntity.ok(response);
        } catch (AccountNotActivatedException e) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ErrorResponse("Account not activated"));
        } catch (InvalidUsernameException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Invalid username or password"));
        }

    }


}
