package com.grabieckacper.ecommerce.dashboard.configuration;

import com.grabieckacper.ecommerce.dashboard.common.EmployeeRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorizationManagerFactory;
import org.springframework.security.authorization.DefaultAuthorizationManagerFactory;

@Configuration
public class RoleConfiguration {
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withDefaultRolePrefix()
                .role(EmployeeRole.ADMIN.name()).implies(EmployeeRole.WORKER.name())
                .build();
    }

    @Bean
    public <T>AuthorizationManagerFactory<T> authorizationManagerFactory() {
        DefaultAuthorizationManagerFactory<T> authorizationManagerFactory = new DefaultAuthorizationManagerFactory<>();
        authorizationManagerFactory.setRoleHierarchy(roleHierarchy());

        return authorizationManagerFactory;
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        defaultMethodSecurityExpressionHandler.setAuthorizationManagerFactory(authorizationManagerFactory());

        return defaultMethodSecurityExpressionHandler;
    }
}
