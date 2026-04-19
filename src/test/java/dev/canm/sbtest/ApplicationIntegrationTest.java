package dev.canm.sbtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.canm.sbtest.employee.EmployeeRepository;
import dev.canm.sbtest.employee.dto.EmployeeRequest;
import dev.canm.sbtest.employee.dto.EmployeeResponse;
import dev.canm.sbtest.employee.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringBootTestApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
class ApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void beforeEach() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_returnStatus200_when_getEmployeesRequest() throws Exception {
        // GIVEN
        final Employee bob = new Employee();
        bob.setName("bob");
        bob.setBirthday(LocalDate.of(1990, 1, 1));
        employeeRepository.save(bob);
        final ResultMatcher nameMatcher = jsonPath("$[0].name", is("bob"));

        // WHEN
        final MockHttpServletRequestBuilder getEmployees = get("/employees").contentType(MediaType.APPLICATION_JSON);

        // THEN
        mockMvc.perform(getEmployees)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(nameMatcher);
    }

    @Test
    void should_createAnEmployee_when_postEmployeesCalled() throws Exception {
        // GIVEN
        final String expectedName = "Bob";
        final LocalDate expectedBirthday = LocalDate.of(1990, 1, 1);
        final EmployeeRequest request = new EmployeeRequest(expectedName, expectedBirthday);
        final String requestJson = objectMapper.writeValueAsString(request);

        final ResultMatcher nameMatcher = jsonPath("$.name", is(expectedName));

        // WHEN
        final MockHttpServletRequestBuilder postEmployee = post("/employees")
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON);

        // THEN
        final MvcResult mvcResult = mockMvc.perform(postEmployee)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(nameMatcher)
                .andReturn();
        final String responseJson = mvcResult.getResponse().getContentAsString();
        final EmployeeResponse actualEmployee = objectMapper.readValue(responseJson, EmployeeResponse.class);
        assertThat(actualEmployee.id()).isNotNull();
        assertThat(actualEmployee.birthday()).isEqualTo(expectedBirthday);
        assertThat(actualEmployee.name()).isEqualTo(expectedName);
    }

}
