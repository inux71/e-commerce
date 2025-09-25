package com.grabieckacper.ecommerce.app.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "app-rsa")
public record RSAKeyProperties(RSAPrivateKey privateKey, RSAPublicKey publicKey) {}
