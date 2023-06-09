package dev.canm.sbtest;

import dev.canm.sbtest.employee.EmployeeRepository;
import dev.canm.sbtest.employee.model.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class EmployeeRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void should_returnEmployeeEntities_when_getFindAllIsCalled() {
        // GIVEN
        final Employee expectedEmployee = new Employee();
        expectedEmployee.setName("Bob");
        expectedEmployee.setBirthday(new Date());

        entityManager.persist(expectedEmployee);
        entityManager.flush();

        // WHEN
        final List<Employee> actualEmployees = employeeRepository.findAll();

        // THEN
        assertNotNull(actualEmployees);
        assertEquals(1, actualEmployees.size());
        final Employee actualEmployee = actualEmployees.get(0);
        assertEquals(expectedEmployee.getId(), actualEmployee.getId());
        assertEquals(expectedEmployee.getName(), actualEmployee.getName());
        assertEquals(expectedEmployee.getBirthday(), actualEmployee.getBirthday());
    }

}
