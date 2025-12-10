package com.grabieckacper.ecommerce.dashboard.service;

import com.grabieckacper.ecommerce.dashboard.model.Employee;
import com.grabieckacper.ecommerce.dashboard.repository.EmployeeRepository;
import com.grabieckacper.ecommerce.shared.exception.UnauthorizedException;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void changePassword(String password) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (authentication == null) {
            throw new UnauthorizedException();
        }

        String email = authentication.getName();

        Employee employee = employeeRepository.findByEmail(email)
                .orElseThrow(()  -> new UsernameNotFoundException("User with email: " + email + " not found"));
        employee.setPassword(passwordEncoder.encode(password));

        employeeRepository.save(employee);
    }

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(username)
                .orElseThrow(()  -> new UsernameNotFoundException("User with email: " + username + " not found"));
    }
}
