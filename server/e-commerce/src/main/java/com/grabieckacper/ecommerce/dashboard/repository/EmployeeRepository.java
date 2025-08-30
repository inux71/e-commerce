package com.grabieckacper.ecommerce.dashboard.repository;

import com.grabieckacper.ecommerce.dashboard.model.Employee;
import com.grabieckacper.ecommerce.shared.repository.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends UserRepository<Employee, Long> {

}
