package com.app.pharmacy.controller;

import com.app.pharmacy.config.KeycloakConfig;
import com.app.pharmacy.config.TestSecurityConfig;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.service.KeycloakAdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private KeycloakAdminService keycloakAdminService;

    @MockBean
    private JwtDecoder jwtDecoder;

    @DisplayName("Create an employee: "
            + "givenCreateEmployeeRequest"
            + "_whenCallCreateEmployeeApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenCreateEmployeeRequest_whenCallCreateEmployeeApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        Mockito.when(keycloakAdminService.createUser(ArgumentMatchers.any(CreateEmployeeRequest.class))).thenReturn("123");

        var registerRequest = """
                {
                  "username": "john_doe",
                  "password": "password123",
                  "firstName": "John",
                  "lastName": "Doe",
                  "birthDate": "1990-05-15",
                  "age": 34,
                  "sex": "M",
                  "type": "Pharmacist",
                  "address": "1234 Elm Street, Springfield, USA",
                  "mail": "john.doe@example.com",
                  "phoneNo": "+1234567890",
                  "salary": 65000.00
                }""";

        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(registerRequest))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                        {
                          "message": "Created employee!",
                          "data": {
                            "id": "123",
                            "username": "john_doe",
                            "firstName": "John",
                            "lastName": "Doe",
                            "birthDate": "1990-05-15",
                            "age": 34,
                            "sex": "M",
                            "type": "Pharmacist",
                            "joinDate": "1970-01-01T00:00:00",
                            "address": "1234 Elm Street, Springfield, USA",
                            "mail": "john.doe@example.com",
                            "phoneNo": "+1234567890",
                            "salary": 65000.00
                          }
                        }
                        """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE);
                });
    }

    @DisplayName("Delete an employee: "
            + "givenEmployeeId"
            + "_whenCallDeleteEmployeeApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenEmployeeId_whenCallDeleteEmployeeApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        Mockito.when(keycloakAdminService.createUser(ArgumentMatchers.any(CreateEmployeeRequest.class))).thenReturn("123");

        mockMvc.perform(delete("/api/v1/employees/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                        {
                          "message": "Deleted employee!"
                        }
                        """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }
}
