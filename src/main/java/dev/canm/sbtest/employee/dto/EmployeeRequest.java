package dev.canm.sbtest.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EmployeeRequest(
        @NotBlank @Size(min = 3, max = 20) String name,
        @NotNull LocalDate birthday) {
}
