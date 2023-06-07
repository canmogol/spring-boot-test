package dev.canm.sbtest.employee;

import dev.canm.sbtest.employee.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeNoOpService implements EmployeeService {

    private static final List<Employee> employees = new ArrayList<>();

    @Override
    public List<Employee> getAllEmployees() {
        return employees;
    }

    @Override
    public Employee save(final Employee employee) {
        employee.setId((long) (employees.size() + 1));
        employees.add(employee);
        return employee;
    }

}
