package com.grabieckacper.ecommerce.dashboard.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "admin")
public record AdminProperties(String email, String password) {}
