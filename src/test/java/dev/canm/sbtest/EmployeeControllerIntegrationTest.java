package dev.canm.sbtest;

import dev.canm.sbtest.employee.EmployeeRestController;
import dev.canm.sbtest.employee.EmployeeService;
import dev.canm.sbtest.employee.dto.EmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.doThrow;

@WebMvcTest(EmployeeRestController.class)
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void should_returnEmployeeList_when_getAllEmployeesCalled() throws Exception {
        // GIVEN
        final EmployeeDTO employee = new EmployeeDTO(1L, "Bob", LocalDate.now());
        when(employeeService.getAllEmployees()).thenReturn(List.of(employee));

        // WHEN / THEN
        mockMvc.perform(
                        get("/employees")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is(employee.name())))
                .andExpect(jsonPath("$[0].birthday").exists());
    }

    @Test
    void should_returnStatus500_when_serviceThrowsUnexpectedException() throws Exception {
        // GIVEN
        doThrow(new RuntimeException("unexpected")).when(employeeService).getAllEmployees();

        // WHEN / THEN
        mockMvc.perform(get("/employees").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code", is("INTERNAL_ERROR")));
    }

}
