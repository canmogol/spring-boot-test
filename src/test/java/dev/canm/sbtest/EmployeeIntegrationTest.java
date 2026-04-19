package dev.canm.sbtest;

import dev.canm.sbtest.employee.EmployeeService;
import dev.canm.sbtest.employee.EmployeeTestContextConfiguration;
import dev.canm.sbtest.employee.dto.EmployeeDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(EmployeeTestContextConfiguration.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
class EmployeeIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void should_returnEmployeeList_when_getAllEmployeesCalled() {
        // WHEN
        final List<EmployeeDTO> allEmployees = employeeService.getAllEmployees();

        // THEN
        assertThat(allEmployees).isEmpty();
    }

    @Test
    void should_saveEmployee_when_anEmployeeProvided() {
        // GIVEN
        final String expectedName = "Bob";
        final LocalDate expectedBirthday = LocalDate.of(2000, 1, 1);
        final EmployeeDTO request = new EmployeeDTO(null, expectedName, expectedBirthday);

        // WHEN
        final EmployeeDTO actualEmployee = employeeService.save(request);

        // THEN
        assertNotNull(actualEmployee);
        assertNotNull(actualEmployee.id());
        assertEquals(expectedName, actualEmployee.name());
        assertEquals(expectedBirthday, actualEmployee.birthday());
    }

}
