package com.grabieckacper.ecommerce;

import com.grabieckacper.ecommerce.dashboard.configuration.AdminProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        AdminProperties.class,
        com.grabieckacper.ecommerce.dashboard.configuration.RSAKeyProperties.class,
        com.grabieckacper.ecommerce.app.configuration.RSAKeyProperties.class
})
public class ECommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
    }
}
