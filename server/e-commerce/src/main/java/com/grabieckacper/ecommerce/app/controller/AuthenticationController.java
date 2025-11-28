package com.grabieckacper.ecommerce.app.controller;

import com.grabieckacper.ecommerce.app.service.AuthenticationService;
import com.grabieckacper.ecommerce.shared.request.LoginRequest;
import com.grabieckacper.ecommerce.shared.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "app-authentication-controller")
@RequestMapping("/api/app/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;

    public AuthenticationController(
            @Qualifier("app-authentication-manager") AuthenticationManager authenticationManager,
            @Qualifier("app-authentication-service") AuthenticationService authenticationService
    ) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
        );
        String jwtToken = authenticationService.generateJwtToken(authentication);
        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String jwtToken = authenticationService.generateJwtToken(authentication);
        LoginResponse loginResponse = new LoginResponse(jwtToken);

        return ResponseEntity.ok(loginResponse);
    }
}
