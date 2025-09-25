package com.grabieckacper.ecommerce.app.configuration;

import com.grabieckacper.ecommerce.app.service.CustomerService;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

@Configuration(value = "app-authentication-configuration")
public class AuthenticationConfiguration {
    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final RSAKeyProperties rsaKeyProperties;

    public AuthenticationConfiguration(
            CustomerService customerService,
            PasswordEncoder passwordEncoder,
            RSAKeyProperties rsaKeyProperties
    ) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Bean(name = "app-authentication-manager")
    @Primary
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(customerService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean(name = "app-jwt-decoder")
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.publicKey())
                .build();
    }

    @Bean(name = "app-jwt-encoder")
    public JwtEncoder jwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.publicKey())
                .privateKey(rsaKeyProperties.privateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwkSource);
    }
}
