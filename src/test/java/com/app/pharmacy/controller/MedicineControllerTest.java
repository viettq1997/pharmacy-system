package com.app.pharmacy.controller;

import com.app.pharmacy.config.TestSecurityConfig;
import com.app.pharmacy.domain.common.ApiResponse;
import com.app.pharmacy.domain.dto.medicine.MedicineResponse;
import com.app.pharmacy.service.KeycloakAdminService;
import com.fasterxml.jackson.core.type.TypeReference;
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
public class MedicineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private KeycloakAdminService keycloakAdminService;

    @MockBean
    private JwtDecoder jwtDecoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get Medicines: "
            + "givenGetMedicineRequest"
            + "_whenCallGetMedicineApi"
            + "_thenReturnMedicinesResponse")
    @Test
    @WithMockUser
    public void givenGetMedicineRequest_whenCallGetMedicineApi_thenReturnMedicinesResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createMedicineRequest = """
                {
                  "name": "Aspirin",
                  "quantity": 100,
                  "price": 19.99,
                  "category": "Pain Relief",
                  "locationRack": "Rack A1"
                }
                """;
        createMedicine(createMedicineRequest).andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/medicines")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                            {
                               "message": "Fetched medicines",
                               "data": {
                                 "content": [
                                   {
                                     "name": "Aspirin",
                                     "quantity": 100,
                                     "price": 19.99,
                                     "category": "Pain Relief",
                                     "locationRack": "Rack A1",
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

    @DisplayName("Create a Medicine: "
            + "givenCreateMedicineRequest"
            + "_whenCallCreateMedicineApi"
            + "_thenReturnMedicineResponse")
    @Test
    @WithMockUser
    public void givenCreateMedicineRequest_whenCallCreateMedicineApi_thenReturnMedicineResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createMedicineRequest = """
                {
                  "name": "Aspirin",
                  "quantity": 100,
                  "price": 19.99,
                  "category": "Pain Relief",
                  "locationRack": "Rack A1"
                }
                """;

        createMedicine(createMedicineRequest)
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                            {
                               "message": "Created medicine!",
                               "data": {
                                 "name": "Aspirin",
                                 "quantity": 100,
                                 "price": 19.99,
                                 "category": "Pain Relief",
                                 "locationRack": "Rack A1",
                                 "createdDate": "1970-01-01T00:00:00",
                                 "createdBy": "user"
                               }
                             }
                            """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }

    private ResultActions createMedicine(String createMedicineRequest) throws Exception {
        return mockMvc.perform(post("/api/v1/medicines")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMedicineRequest));
    }

    @DisplayName("Delete a Medicine: "
            + "givenMedicineId"
            + "_whenCallDeleteMedicineApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenMedicineId_whenCallDeleteMedicineApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createMedicineRequest = """
                {
                  "name": "Aspirin",
                  "quantity": 100,
                  "price": 19.99,
                  "category": "Pain Relief",
                  "locationRack": "Rack A1"
                }
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createMedicine(createMedicineRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });
        mockMvc.perform(delete("/api/v1/medicines/{id}", id.get())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = String.format("""
                            {
                              "message": "Deleted medicine!",
                              "data": {
                                "id": "%s"
                              }
                            }
                            """, id);
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE);
                });
    }

    @DisplayName("Update a Medicine: "
            + "givenMedicineIdAndMedicineInfo"
            + "_whenCallUpdateMedicineApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenMedicineIdAndMedicineInfo_whenCallUpdateMedicineApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        var createMedicineRequest = """
                {
                  "name": "Aspirin",
                  "quantity": 100,
                  "price": 19.99,
                  "category": "Pain Relief",
                  "locationRack": "Rack A1"
                }
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createMedicine(createMedicineRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });

        var updateMedicineRequest = """
                {
                  "name": "Aspirin1",
                  "quantity": 100,
                  "price": 19.99,
                  "category": "Pain Relief",
                  "locationRack": "Rack A1"
                }
                """;
        mockMvc.perform(put("/api/v1/medicines/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateMedicineRequest))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                        {
                               "message": "Updated medicine!",
                               "data": {
                                 "name": "Aspirin1",
                                 "quantity": 100,
                                 "price": 19.99,
                                 "category": "Pain Relief",
                                 "locationRack": "Rack A1",
                                 "createdDate": "1970-01-01T00:00:00",
                                 "createdBy": "user",
                                 "updatedDate": "1970-01-01T00:00:00",
                                 "updatedBy": "user"
                               }
                             }
                        """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }
}