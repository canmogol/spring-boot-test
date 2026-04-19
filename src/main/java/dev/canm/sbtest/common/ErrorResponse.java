package dev.canm.sbtest.common;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(
        String code,
        String message,
        Instant timestamp,
        String path,
        List<FieldError> errors) {

    public record FieldError(String field, String message) { }

    public static ErrorResponse of(
            final String code,
            final String message,
            final String path) {
        return new ErrorResponse(code, message, Instant.now(), path, List.of());
    }

    public static ErrorResponse of(
            final String code,
            final String message,
            final String path,
            final List<FieldError> errors) {
        return new ErrorResponse(code, message, Instant.now(), path, errors);
    }
}
