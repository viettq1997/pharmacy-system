package com.app.pharmacy.controller;

import com.app.pharmacy.config.TestSecurityConfig;
import com.app.pharmacy.domain.dto.employee.CreateEmployeeRequest;
import com.app.pharmacy.service.KeycloakAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    private final ObjectMapper objectMapper = new ObjectMapper();

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

        var createUserRequest = """
                {
                  "username": "john_doe",
                  "password": "password123",
                  "firstName": "John",
                  "lastName": "Doe",
                  "role": "USER",
                  "birthDate": "1990-05-15",
                  "age": 34,
                  "sex": "M",
                  "type": "Pharmacist",
                  "address": "1234 Elm Street, Springfield, USA",
                  "mail": "john.doe@example.com",
                  "phoneNo": "+1234567890",
                  "salary": 65000.00
                }""";
        createUser(createUserRequest).andExpect(status().isCreated())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                        {
                           "code":null,
                           "message":null,
                           "data": {
                             "id": "123",
                             "username": "john_doe",
                             "firstName": "John",
                             "lastName": "Doe",
                             "role": "USER",
                             "birthDate": "1990-05-15",
                             "age": 34,
                             "sex": "M",
                             "type": "Pharmacist",
                             "joinDate": "1970-01-01T00:00:00",
                             "address": "1234 Elm Street, Springfield, USA",
                             "mail": "john.doe@example.com",
                             "phoneNo": "+1234567890",
                             "salary": 65000.00,
                             "createdDate": "1970-01-01T00:00:00",
                             "createdBy": "user",
                             "updatedDate":null,
                             "updatedBy":null
                           }
                         }
                        """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE);
                });
    }

    private ResultActions createUser(String createUserRequest) throws Exception {
         return mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createUserRequest));
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
        Mockito.doNothing().when(keycloakAdminService).deleteUser(ArgumentMatchers.anyString());
        Mockito.when(keycloakAdminService.createUser(ArgumentMatchers.any(CreateEmployeeRequest.class))).thenReturn("124");

        var createUserRequest = """
                {
                  "username": "john_doe1",
                  "password": "password123",
                  "firstName": "John",
                  "lastName": "Doe",
                  "role": "USER",
                  "birthDate": "1990-05-15",
                  "age": 34,
                  "sex": "M",
                  "type": "Pharmacist",
                  "address": "1234 Elm Street, Springfield, USA",
                  "mail": "john.doe@example.com",
                  "phoneNo": "+1234567890",
                  "salary": 65000.00
                }""";
        AtomicReference<String> id = new AtomicReference<>("");
        createUser(createUserRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });
        mockMvc.perform(delete("/api/v1/employees/124")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = String.format("""
                            {
                              "code":null,
                              "message":null,
                              "data": {
                                "id": "%s"
                              }
                            }
                            """, id);
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE);
                });
    }

    @DisplayName("Update an employee: "
            + "givenEmployeeIdAndEmployeeInfo"
            + "_whenCallUpdateEmployeeApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenEmployeeIdAndEmployeeInfo_whenCallUpdateEmployeeApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        Mockito.doNothing().when(keycloakAdminService).deleteUser(ArgumentMatchers.anyString());
        Mockito.when(keycloakAdminService.createUser(ArgumentMatchers.any(CreateEmployeeRequest.class))).thenReturn("124");

        var createUserRequest = """
                {
                  "username": "john_doe1",
                  "password": "password123",
                  "firstName": "John",
                  "lastName": "Doe",
                  "role": "USER",
                  "birthDate": "1990-05-15",
                  "age": 34,
                  "sex": "M",
                  "type": "Pharmacist",
                  "address": "1234 Elm Street, Springfield, USA",
                  "mail": "john.doe@example.com",
                  "phoneNo": "+1234567890",
                  "salary": 65000.00
                }""";
        createUser(createUserRequest).andExpect(status().isCreated());

        var updateUserRequest = """
                {
                  "username": "john_doe1",
                  "firstName": "John111",
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
        mockMvc.perform(put("/api/v1/employees/124")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUserRequest))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                        {
                          "data": {
                            "id": "124",
                            "username": "john_doe1",
                            "firstName": "John111",
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
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }

    @DisplayName("Get Medicines: "
            + "givenGetMedicineRequest"
            + "_whenCallGetMedicineApi"
            + "_thenReturnMedicinesResponse")
    @Test
    @WithMockUser
    public void givenGetMedicineRequest_whenCallGetMedicineApi_thenReturnMedicinesResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        Mockito.when(keycloakAdminService.createUser(ArgumentMatchers.any(CreateEmployeeRequest.class))).thenReturn("testGet");
        var createUserRequest = """
                {
                  "username": "john_doe1",
                  "password": "password123",
                  "firstName": "John",
                  "lastName": "Doe",
                  "role": "USER",
                  "birthDate": "1990-05-15",
                  "age": 34,
                  "sex": "M",
                  "type": "Pharmacist",
                  "address": "1234 Elm Street, Springfield, USA",
                  "mail": "john.doe@example.com",
                  "phoneNo": "+1234567890",
                  "salary": 65000.00
                }""";
        createUser(createUserRequest).andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                        {
                          "data": {
                            "content": [
                              {
                                "id": "testGet",
                                "username": "john_doe1",
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
                            ],
                            "size": 10,
                            "number": 0,
                            "totalElement": 1
                          }
                        }
                        """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }
}
