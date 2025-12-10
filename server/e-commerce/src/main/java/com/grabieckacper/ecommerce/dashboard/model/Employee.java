package com.grabieckacper.ecommerce.dashboard.model;

import com.grabieckacper.ecommerce.dashboard.common.EmployeeRole;
import com.grabieckacper.ecommerce.shared.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Employee")
public class Employee extends User {
    @NotNull
    private EmployeeRole role = EmployeeRole.WORKER; // Employee is a worker by default

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    @Override
    @NullMarked
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
}
