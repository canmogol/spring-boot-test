package dev.canm.sbtest;

import dev.canm.sbtest.employee.EmployeeRepository;
import dev.canm.sbtest.employee.EmployeeService;
import dev.canm.sbtest.employee.dto.EmployeeDTO;
import dev.canm.sbtest.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @Test
    void should_returnEmployeeList_when_getAllEmployeesCalled() {
        // GIVEN
        Employee expectedEmployee = new Employee();
        expectedEmployee.setId(1L);
        expectedEmployee.setName("Bob");
        expectedEmployee.setBirthday(LocalDate.now());
        when(employeeRepository.findAll()).thenReturn(List.of(expectedEmployee));

        // WHEN
        final List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();

        // THEN
        assertNotNull(allEmployees);
        assertFalse(allEmployees.isEmpty());
        assertEquals(1, allEmployees.size());
        final EmployeeDTO actualEmployee = allEmployees.get(0);
        assertEquals(expectedEmployee.getId(), actualEmployee.id());
        assertEquals(expectedEmployee.getName(), actualEmployee.name());
        assertEquals(expectedEmployee.getBirthday(), actualEmployee.birthday());
    }

}
