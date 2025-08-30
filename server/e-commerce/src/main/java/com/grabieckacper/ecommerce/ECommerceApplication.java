package com.grabieckacper.ecommerce;

import com.grabieckacper.ecommerce.dashboard.configuration.RSAKeyProperties;
import com.grabieckacper.ecommerce.dashboard.configuration.AdminProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({AdminProperties.class, RSAKeyProperties.class})
public class ECommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}
