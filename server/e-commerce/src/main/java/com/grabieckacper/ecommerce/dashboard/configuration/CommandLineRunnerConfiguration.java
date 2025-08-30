package com.grabieckacper.ecommerce.dashboard.configuration;

import com.grabieckacper.ecommerce.dashboard.common.EmployeeRole;
import com.grabieckacper.ecommerce.dashboard.model.Employee;
import com.grabieckacper.ecommerce.dashboard.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CommandLineRunnerConfiguration {
    private final AdminProperties adminProperties;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public CommandLineRunnerConfiguration(
            AdminProperties adminProperties, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder
    ) {
        this.adminProperties = adminProperties;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean // insert admin to the database
    public CommandLineRunner commandLineRunner() {
        return _ -> employeeRepository.findByEmail(adminProperties.email())
                .orElseGet(() -> {
                    Employee employee = new Employee();
                    employee.setEmail(adminProperties.email());
                    employee.setPassword(passwordEncoder.encode(adminProperties.password()));
                    employee.setRole(EmployeeRole.ADMIN);

                    return employeeRepository.save(employee);
                });
    }
}
