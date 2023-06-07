package dev.canm.sbtest;

import dev.canm.sbtest.employee.EmployeeService;
import dev.canm.sbtest.employee.EmployeeTestContextConfiguration;
import dev.canm.sbtest.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Import(EmployeeTestContextConfiguration.class)
class EmployeeIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void should_returnEmployeeList_when_getAllEmployeesCalled() {
        // WHEN
        final List<Employee> allEmployees = employeeService.getAllEmployees();

        // THEN
        assertNotNull(allEmployees);
    }

    @Test
    void should_saveEmployee_when_anEmployeeProvided() {
        // GIVEN
        final String expectedName = "Bob";
        final java.util.Date expectedBirthday = Date.from(Instant.parse("2000-01-01T00:00:00.00Z"));
        final Employee expectedEmployee = new Employee();
        expectedEmployee.setName(expectedName);
        expectedEmployee.setBirthday(expectedBirthday);

        // WHEN
        final Employee actualEmployee = employeeService.save(expectedEmployee);

        // THEN
        assertNotNull(actualEmployee);
        assertNotNull(actualEmployee.getId());
        assertEquals(expectedName, actualEmployee.getName());
        assertEquals(expectedBirthday, actualEmployee.getBirthday());
    }

}
