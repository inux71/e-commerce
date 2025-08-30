package com.grabieckacper.ecommerce.dashboard.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "dashboard-rsa")
public record RSAKeyProperties(RSAPrivateKey privateKey, RSAPublicKey publicKey) {}
