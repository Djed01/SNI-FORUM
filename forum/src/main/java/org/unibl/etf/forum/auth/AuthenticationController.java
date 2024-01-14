package org.unibl.etf.forum.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.forum.models.entities.UserEntity;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<TempAuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
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
