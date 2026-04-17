package dev.canm.sbtest.employee.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeTest {

    @Test
    void should_beEqual_when_sameIdProvided() {
        final Employee employee1 = new Employee();
        employee1.setId(1L);
        final Employee employee2 = new Employee();
        employee2.setId(1L);

        assertThat(employee1).isEqualTo(employee2);
        assertThat(employee1.hashCode()).isEqualTo(employee2.hashCode());
    }

    @Test
    void should_notBeEqual_when_differentIdProvided() {
        final Employee employee1 = new Employee();
        employee1.setId(1L);
        final Employee employee2 = new Employee();
        employee2.setId(2L);

        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void should_notBeEqual_when_idIsNull() {
        final Employee employee1 = new Employee();
        final Employee employee2 = new Employee();

        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void should_notBeEqual_when_comparedToDifferentType() {
        final Employee employee = new Employee();
        employee.setId(1L);

        assertThat(employee).isNotEqualTo("not-an-employee");
    }

    @Test
    void should_containIdAndName_when_toStringCalled() {
        final Employee employee = new Employee();
        employee.setId(1L);
        employee.setName("Bob");

        assertThat(employee.toString()).contains("1", "Bob");
    }

}
