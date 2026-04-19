package dev.canm.sbtest.employee;

import dev.canm.sbtest.employee.dto.EmployeeDTO;
import dev.canm.sbtest.employee.dto.EmployeeRequest;
import dev.canm.sbtest.employee.dto.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    public EmployeeRestController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees().stream()
                .map(this::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody @Valid final EmployeeRequest request) {
        final EmployeeDTO saved = employeeService.save(toDTO(request));
        final URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.id())
                .toUri();
        return ResponseEntity.created(location).body(toResponse(saved));
    }

    private EmployeeDTO toDTO(final EmployeeRequest request) {
        return new EmployeeDTO(null, request.name(), request.birthday());
    }

    private EmployeeResponse toResponse(final EmployeeDTO dto) {
        return new EmployeeResponse(dto.id(), dto.name(), dto.birthday());
    }

}
