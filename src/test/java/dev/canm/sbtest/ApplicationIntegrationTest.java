package dev.canm.sbtest;

import com.fasterxml.jackson.databind.json.JsonMapper;
import dev.canm.sbtest.employee.EmployeeRepository;
import dev.canm.sbtest.employee.model.Employee;
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

import java.time.Instant;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// We use @SpringBootTest to bootstrap the entire container.
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringBootTestApplication.class
)
// We use @AutoConfigureMockMvc to enable and configure autoconfiguration of MockMvc, so that we have an instance of MockMvc.
@AutoConfigureMockMvc
// We use @TestPropertySource to configure the locations of properties files. The properties file will override the existing application.properties file.
@TestPropertySource(locations = "classpath:application-integration-test.properties")
class ApplicationIntegrationTest {

    // MockMvc is the main entry point for server-side Spring MVC test support.
    // Since Autowired fields are injected right after construction of a bean such a config field does not have to be public.
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    private JsonMapper jsonMapper = new JsonMapper();

    @Test
    void should_returnStatus200_when_getEmployeesRequest() throws Exception {
        // GIVEN
        // We create an employee and save it to the database.
        final Employee bob = new Employee();
        bob.setName("bob");
        bob.setBirthday(Date.from(Instant.parse("1990-01-01T23:59:59.00Z")));
        employeeRepository.save(bob);
        // The matcher should match the name of the first item to be bob.
        final ResultMatcher nameMatcher = jsonPath("$[0].name", is("bob"));

        // WHEN
        // The request is a GET request to the /employees endpoint.
        final MockHttpServletRequestBuilder getEmployees = get("/employees").contentType(MediaType.APPLICATION_JSON);

        // THEN
        // The response is expected to be a JSON array with a single item.
        mockMvc.perform(getEmployees)
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(nameMatcher);
    }

    @Test
    void should_createAnEmployee_when_postEmployeesCalled() throws Exception {
        // GIVEN
        // We create an employee and save it to the database.
        final Employee employee = new Employee();
        final String expectedName = "Bob";
        employee.setName(expectedName);
        employee.setBirthday(Date.from(Instant.parse("1990-01-01T23:59:59.00Z")));
        final String employeeString = jsonMapper.writeValueAsString(employee);
        final String expectedEmployeeJSON = jsonMapper.writeValueAsString(employee);

        // The matcher should match the name of the first item to be bob.
        final ResultMatcher nameMatcher = jsonPath("$.name", is(expectedName));

        // WHEN
        // The request is a POST request to the /employees endpoint with the employee object as the body.
        final MockHttpServletRequestBuilder postEmployee = post("/employees")
                .content(employeeString)
                .contentType(MediaType.APPLICATION_JSON);

        // THEN
        // The response is expected to be a JSON array with a single item.
        final MvcResult mvcResult = mockMvc.perform(postEmployee)
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(nameMatcher)
                .andReturn();
        // We can get the content of the response as a string, convert it to an Employee object and check the values.
        final String actualEmployeeJSON = mvcResult.getResponse().getContentAsString();
        final Employee actualEmployee = jsonMapper.readValue(actualEmployeeJSON, Employee.class);
        assertNotNull(actualEmployee.getId());
        assertEquals(employee.getBirthday(), actualEmployee.getBirthday());
        assertEquals(employee.getName(), actualEmployee.getName());
    }

}
