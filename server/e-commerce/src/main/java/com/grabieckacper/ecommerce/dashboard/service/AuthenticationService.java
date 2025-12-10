package com.grabieckacper.ecommerce.dashboard.service;

import com.grabieckacper.ecommerce.dashboard.model.Employee;
import com.grabieckacper.ecommerce.shared.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service(value = "dashboard-authentication-service")
public class AuthenticationService {
    private final JwtEncoder jwtEncoder;

    public AuthenticationService(@Qualifier("dashboard-jwt-encoder") JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateJwtToken(Authentication authentication) {
        Employee employee = (Employee) authentication.getPrincipal();

        if (employee == null) {
            throw new UnauthorizedException();
        }

        Instant now = Instant.now();
        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuer("e-commerce-dashboard")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(authentication.getName())
                .claim("id", employee.getId())
                .claim("email", employee.getUsername())
                .claim("role", authorities)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet))
                .getTokenValue();
    }
}
