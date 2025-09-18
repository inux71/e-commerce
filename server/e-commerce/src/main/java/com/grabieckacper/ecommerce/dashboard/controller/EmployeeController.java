package com.grabieckacper.ecommerce.dashboard.controller;

import com.grabieckacper.ecommerce.dashboard.service.EmployeeService;
import com.grabieckacper.ecommerce.shared.request.ChangePasswordRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        employeeService.changePassword(changePasswordRequest.password());

        return ResponseEntity.noContent().build();
    }
}
