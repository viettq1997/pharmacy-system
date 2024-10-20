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
public class SupplierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private KeycloakAdminService keycloakAdminService;

    @MockBean
    private JwtDecoder jwtDecoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get Supplier: "
            + "givenGetSupplierRequest"
            + "_whenCallGetSupplierApi"
            + "_thenReturnSuppliersResponse")
    @Test
    @WithMockUser
    public void givenGetSupplierRequest_whenCallGetSupplierApi_thenReturnSuppliersResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createSupplierRequest = """
                {
                  "name": "ABC Suppliers test",
                  "address": "1234 Main Street, Springfield",
                  "phoneNo": "123-456-7890",
                  "mail": "abc@suppliers.com"
                }
                """;
        createSupplier(createSupplierRequest).andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/suppliers?name={name}", "ABC Suppliers test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                            {
                               "data": {
                                 "content": [
                                   {
                                      "name": "ABC Suppliers test",
                                      "address": "1234 Main Street, Springfield",
                                      "phoneNo": "123-456-7890",
                                      "mail": "abc@suppliers.com",
                                      "createdDate": "1970-01-01T00:00:00",
                                      "createdBy": "user"
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

    @DisplayName("Create a Supplier: "
            + "givenCreateSupplierRequest"
            + "_whenCallCreateSupplierApi"
            + "_thenReturnSupplierResponse")
    @Test
    @WithMockUser
    public void givenCreateSupplierRequest_whenCallCreateSupplierApi_thenReturnSupplierResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createSupplierRequest = """
                {
                  "name": "ABC Suppliers",
                  "address": "1234 Main Street, Springfield",
                  "phoneNo": "123-456-7890",
                  "mail": "abc@suppliers.com"
                }
                """;
        createSupplier(createSupplierRequest)
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                            {
                               "data": {
                                  "name": "ABC Suppliers",
                                  "address": "1234 Main Street, Springfield",
                                  "phoneNo": "123-456-7890",
                                  "mail": "abc@suppliers.com",
                                  "createdDate": "1970-01-01T00:00:00",
                                  "createdBy": "user"
                                }
                             }
                            """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }

    private ResultActions createSupplier(String createSupplierRequest) throws Exception {
        return mockMvc.perform(post("/api/v1/suppliers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createSupplierRequest));
    }

    @DisplayName("Delete a Supplier: "
            + "givenSupplierId"
            + "_whenCallDeleteSupplierApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenSupplierId_whenCallDeleteSupplierApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createSupplierRequest = """
                {
                  "name": "ABC Suppliers",
                  "address": "1234 Main Street, Springfield",
                  "phoneNo": "123-456-7890",
                  "mail": "abc@suppliers.com"
                }
                
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createSupplier(createSupplierRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });
        mockMvc.perform(delete("/api/v1/suppliers/{id}", id.get())
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

    @DisplayName("Update a Supplier: "
            + "givenSupplierIdAndSupplierInfo"
            + "_whenCallUpdateSupplierApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenSupplierIdAndSupplierInfo_whenCallUpdateSupplierApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        var createSupplierRequest = """
                {
                  "name": "ABC Suppliers",
                  "address": "1234 Main Street, Springfield",
                  "phoneNo": "123-456-7890",
                  "mail": "abc@suppliers.com"
                }
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createSupplier(createSupplierRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });

        var updateSupplierRequest = """
                {
                  "name": "ABC Suppliers",
                  "address": "1234 Main Street, Springfield",
                  "phoneNo": "123-456-7890",
                  "mail": "abcc@suppliers.com"
                }
                """;
        mockMvc.perform(put("/api/v1/suppliers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateSupplierRequest))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = String.format("""
                        {
                           "code":null,
                           "message":null,
                           "data": {
                                "id": "%s",
                                "name": "ABC Suppliers",
                                "address": "1234 Main Street, Springfield",
                                "phoneNo": "123-456-7890",
                                "mail": "abcc@suppliers.com",
                                "createdDate": "1970-01-01T00:00:00",
                                "createdBy": "user",
                                "updatedDate": "1970-01-01T00:00:00",
                                "updatedBy": "user"
                           }
                         }
                        """, id);
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE);
                });
    }
}
