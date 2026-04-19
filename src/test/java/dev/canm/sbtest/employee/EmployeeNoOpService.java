package dev.canm.sbtest.employee;

import dev.canm.sbtest.employee.dto.EmployeeDTO;

import java.util.ArrayList;
import java.util.List;

public class EmployeeNoOpService implements EmployeeService {

    private final List<EmployeeDTO> employees = new ArrayList<>();
    private long nextId = 1;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employees;
    }

    @Override
    public EmployeeDTO save(final EmployeeDTO dto) {
        final EmployeeDTO saved = new EmployeeDTO(nextId++, dto.name(), dto.birthday());
        employees.add(saved);
        return saved;
    }

}
