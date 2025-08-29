package com.grabieckacper.ecommerce.dashboard.configuration;

import com.grabieckacper.ecommerce.dashboard.common.EmployeeRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class RoleConfiguration {
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(EmployeeRole.ADMIN.name()).implies(EmployeeRole.WORKER.name())
                .build();
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setRoleHierarchy(roleHierarchy());

        return defaultMethodSecurityExpressionHandler;
    }
}
