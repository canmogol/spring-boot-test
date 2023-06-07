package dev.canm.sbtest.employee;

import dev.canm.sbtest.employee.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// When @Transactional annotation is declared at the class level,
// it applies as a default to all methods of the declaring class and its subclasses.
//
// If no custom rollback rules are configured in this annotation,
// the transaction will roll back on RuntimeException and Error but not on CHECKED exceptions.
// Custom rules may be configured via rollbackFor/noRollbackFor and rollbackForClassName/noRollbackForClassName,
// which allow rules to be specified as types or patterns, respectively.
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(final Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
