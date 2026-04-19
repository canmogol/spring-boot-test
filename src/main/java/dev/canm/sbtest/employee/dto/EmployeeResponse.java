package dev.canm.sbtest.employee.dto;

import java.time.LocalDate;

public record EmployeeResponse(Long id, String name, LocalDate birthday) {
}
