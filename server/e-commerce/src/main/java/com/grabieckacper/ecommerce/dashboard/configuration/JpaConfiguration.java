package com.grabieckacper.ecommerce.dashboard.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class JpaConfiguration {
    @Bean
    public AuditorAware<Long> employeeAuditorAware() {
        return new EmployeeAuditorAware();
    }
}
