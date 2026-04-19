package dev.canm.sbtest.employee;

import dev.canm.sbtest.employee.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO save(EmployeeDTO employee);
}
