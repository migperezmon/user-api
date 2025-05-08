package cl.ntt.userapi.user_api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cl.ntt.userapi.user_api.dto.LoginRequest;
import cl.ntt.userapi.user_api.dto.UserResponse;
import cl.ntt.userapi.user_api.services.interfaces.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/v1/auth/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request: {}", loginRequest);
        if (loginRequest.getEmail() == null || loginRequest.getPassword() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(userService.login(loginRequest));

    }

}
