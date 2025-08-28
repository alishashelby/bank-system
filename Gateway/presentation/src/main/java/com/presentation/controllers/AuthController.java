package com.presentation.controllers;

import com.businesslogic.dto.LoginRequest;
import com.businesslogic.dto.LoginResponse;
import com.businesslogic.exceptions.UserNotFoundException;
import com.businesslogic.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest)
            throws UserNotFoundException {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok().body(response);
    }
}
