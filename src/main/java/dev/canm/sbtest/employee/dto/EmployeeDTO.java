package dev.canm.sbtest.employee.dto;

import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record EmployeeDTO(
        @Nullable Long id,
        String name,
        LocalDate birthday) {
}
