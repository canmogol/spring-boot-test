package dev.canm.sbtest.employee;

import dev.canm.sbtest.employee.dto.EmployeeDTO;
import dev.canm.sbtest.employee.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public EmployeeDTO save(final EmployeeDTO dto) {
        final Employee employee = new Employee();
        employee.setName(dto.name());
        employee.setBirthday(dto.birthday());
        final Employee saved = employeeRepository.save(employee);
        return toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    private EmployeeDTO toDTO(final Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getName(), employee.getBirthday());
    }
}
