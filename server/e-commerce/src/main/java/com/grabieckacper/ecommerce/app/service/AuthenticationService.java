package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service(value = "app-authentication-service")
public class AuthenticationService {
    private final JwtEncoder jwtEncoder;

    public AuthenticationService(@Qualifier("app-jwt-encoder") JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateJwtToken(Authentication authentication) {
        Customer customer = (Customer) authentication.getPrincipal();

        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("e-commerce-app")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("id", customer.getId())
                .claim("email", customer.getUsername())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
    }
}
