package com.app.pharmacy.controller;

import com.app.pharmacy.config.TestSecurityConfig;
import com.app.pharmacy.service.KeycloakAdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private KeycloakAdminService keycloakAdminService;

    @MockBean
    private JwtDecoder jwtDecoder;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @DisplayName("Create a Customer: "
            + "givenCreateCustomerRequest"
            + "_whenCallCreateCustomerApi"
            + "_thenReturnCustomerResponse")
    @Test
    @WithMockUser
    public void givenCreateCustomerRequest_whenCallCreateCustomerApi_thenReturnCustomerResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createCustomerRequest = """
                {
                  "firstName": "John",
                  "lastName": "Doe",
                  "age": 30,
                  "sex": "M",
                  "phoneNo": "123-456-7890",
                  "mail": "johndoe@example.com"
                }
                """;
        createCustomer(createCustomerRequest)
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                        {
                            "data": {
                                "firstName": "John",
                                "lastName": "Doe",
                                "age": 30,
                                "sex": "M",
                                "phoneNo": "123-456-7890",
                                "mail": "johndoe@example.com",
                                "createdDate": "1970-01-01T00:00:00",
                                "createdBy": "user"
                            }
                        }
                        """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }

    @DisplayName("Get Customer: "
            + "givenGetCustomerRequest"
            + "_whenCallGetCustomerApi"
            + "_thenReturnCustomersResponse")
    @Test
    @WithMockUser
    public void givenGetCustomerRequest_whenCallGetCustomerApi_thenReturnCustomersResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createCustomerRequest = """
                {
                  "firstName": "John for get",
                  "lastName": "Doe",
                  "age": 30,
                  "sex": "M",
                  "phoneNo": "123-456-7890",
                  "mail": "johndoe@example.com"
                }
                """;
        createCustomer(createCustomerRequest).andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/customers?name={name}", "John for get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                            {
                               "data": {
                                 "content": [
                                   {
                                      "firstName": "John for get",
                                      "lastName": "Doe",
                                      "age": 30,
                                      "sex": "M",
                                      "phoneNo": "123-456-7890",
                                      "mail": "johndoe@example.com"
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

    @DisplayName("Delete a Customer: "
            + "givenCustomerId"
            + "_whenCallDeleteCustomerApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenCustomerId_whenCallDeleteCustomerApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createCustomerRequest = """
                {
                  "firstName": "John",
                  "lastName": "Doe",
                  "age": 30,
                  "sex": "M",
                  "phoneNo": "123-456-7890",
                  "mail": "johndoe@example.com"
                }
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createCustomer(createCustomerRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });
        mockMvc.perform(delete("/api/v1/customers/{id}", id.get())
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

    @DisplayName("Update a Customer: "
            + "givenCustomerIdAndCustomerInfo"
            + "_whenCallUpdateCustomerApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenCustomerIdAndCustomerInfo_whenCallUpdateCustomerApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        var createCustomerRequest = """
                {
                  "firstName": "John",
                  "lastName": "Doe",
                  "age": 30,
                  "sex": "M",
                  "phoneNo": "123-456-7890",
                  "mail": "johndoe@example.com"
                }
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createCustomer(createCustomerRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });

        var updateCustomerRequest = """
                {
                  "firstName": "John updated",
                  "lastName": "Doe",
                  "age": 30,
                  "sex": "M",
                  "phoneNo": "123-456-7890",
                  "mail": "johndoe@example.com"
                }
                """;
        mockMvc.perform(put("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateCustomerRequest))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = String.format("""
                        {
                           "code": null,
                           "message": null,
                           "data": {
                                "id": "%s",
                                "firstName": "John updated",
                                "lastName": "Doe",
                                "age": 30,
                                "sex": "M",
                                "phoneNo": "123-456-7890",
                                "mail": "johndoe@example.com",
                                "createdDate": "1970-01-01T00:00:00",
                                "createdBy": "user",
                                "updatedDate":"1970-01-01T00:00:00",
                                "updatedBy":"user"
                           }
                         }
                        """, id);
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE);
                });
    }

    private ResultActions createCustomer(String createCustomerRequest) throws Exception {
        return mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createCustomerRequest));
    }
}
