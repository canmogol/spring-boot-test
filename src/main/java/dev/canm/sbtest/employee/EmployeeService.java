package dev.canm.sbtest.employee;

import dev.canm.sbtest.employee.model.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAllEmployees();

    Employee save(Employee employee);
}
