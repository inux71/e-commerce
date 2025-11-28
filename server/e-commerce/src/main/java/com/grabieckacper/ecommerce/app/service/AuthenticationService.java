package com.grabieckacper.ecommerce.app.service;

import com.grabieckacper.ecommerce.app.model.Customer;
import com.grabieckacper.ecommerce.app.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service(value = "app-authentication-service")
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final JwtEncoder jwtEncoder;

    public AuthenticationService(
            CustomerRepository customerRepository, @Qualifier("app-jwt-encoder") JwtEncoder jwtEncoder
    ) {
        this.customerRepository = customerRepository;
        this.jwtEncoder = jwtEncoder;
    }

    public String generateJwtToken(Authentication authentication) {
        String email = authentication.getName();
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));


        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("e-commerce-app")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(customer.getUsername())
                .claim("id", customer.getId())
                .claim("email", customer.getUsername())
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
    }
}
