package dev.canm.sbtest.employee;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmployeeTestContextConfiguration {

    @Bean
    public EmployeeService employeeService() {
        return new EmployeeNoOpService();
    }

}