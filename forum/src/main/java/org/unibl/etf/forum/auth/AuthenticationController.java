package org.unibl.etf.forum.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.exceptions.AccountNotActivatedException;
import org.unibl.etf.forum.exceptions.InvalidUsernameException;
import org.unibl.etf.forum.models.entities.UserEntity;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
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
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/verify-2fa")
    public ResponseEntity<JwtAuthResponse> verifyTwoFactorCode(@RequestBody TwoFactorVerificationRequest request) {
        return ResponseEntity.ok(authenticationService.verifyTwoFactorCode(
                request.getUsername(), request.getUserSubmittedCode()));
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/signup")
    public  ResponseEntity<UserEntity> signup(@RequestBody SignUpRequest signUpRequest){
        return ResponseEntity.ok(authenticationService.signup(signUpRequest));
    }
}
