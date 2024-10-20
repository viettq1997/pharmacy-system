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
public class MedicineCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Clock clock;

    @MockBean
    private KeycloakAdminService keycloakAdminService;

    @MockBean
    private JwtDecoder jwtDecoder;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Get MedicineCategory: "
            + "givenGetMedicineCategoryRequest"
            + "_whenCallGetMedicineCategoryApi"
            + "_thenReturnMedicineCategoriesResponse")
    @Test
    @WithMockUser
    public void givenGetMedicineCategoryRequest_whenCallGetMedicineCategoryApi_thenReturnMedicineCategoriesResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createMedicineCategoryRequest = """
                {
                  "name": "Pain Relief for get",
                  "description": "Medicines used for relieving pain."
                }
                """;
        createMedicineCategory(createMedicineCategoryRequest).andExpect(status().isCreated());

        mockMvc.perform(get("/api/v1/medicineCategories?name={name}", "Pain Relief for get")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                            {
                               "data": {
                                 "content": [
                                   {
                                     "name": "Pain Relief for get",
                                     "description": "Medicines used for relieving pain.",
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

    @DisplayName("Create a MedicineCategory: "
            + "givenCreateMedicineCategoryRequest"
            + "_whenCallCreateMedicineCategoryApi"
            + "_thenReturnMedicineCategoryResponse")
    @Test
    @WithMockUser
    public void givenCreateMedicineCategoryRequest_whenCallCreateMedicineCategoryApi_thenReturnMedicineCategoryResponse() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createMedicineCategoryRequest = """
                {
                  "name": "Pain Relief",
                  "description": "Medicines used for relieving pain."
                }
                """;
        createMedicineCategory(createMedicineCategoryRequest)
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = """
                            {
                               "data": {
                                   "name": "Pain Relief",
                                   "description": "Medicines used for relieving pain.",
                                   "createdDate": "1970-01-01T00:00:00",
                                   "createdBy": "user"
                                 }
                             }
                            """;
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
                });
    }

    private ResultActions createMedicineCategory(String createMedicineCategoryRequest) throws Exception {
        return mockMvc.perform(post("/api/v1/medicineCategories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createMedicineCategoryRequest));
    }

    @DisplayName("Delete a MedicineCategory: "
            + "givenMedicineCategoryId"
            + "_whenCallDeleteMedicineCategoryApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenMedicineCategoryId_whenCallDeleteMedicineCategoryApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);

        var createMedicineCategoryRequest = """
                {
                  "name": "Pain Relief",
                  "description": "Medicines used for relieving pain."
                }
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createMedicineCategory(createMedicineCategoryRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });
        mockMvc.perform(delete("/api/v1/medicineCategories/{id}", id.get())
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

    @DisplayName("Update a MedicineCategory: "
            + "givenMedicineCategoryIdAndMedicineCategoryInfo"
            + "_whenCallUpdateMedicineCategoryApi"
            + "_thenReturnSuccess")
    @Test
    @WithMockUser
    public void givenMedicineCategoryIdAndMedicineCategoryInfo_whenCallUpdateMedicineCategoryApi_thenReturnSuccess() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
        var createMedicineCategoryRequest = """
                {
                  "name": "Pain Relief",
                  "description": "Medicines used for relieving pain."
                }
                """;
        AtomicReference<String> id = new AtomicReference<>("");
        createMedicineCategory(createMedicineCategoryRequest).andExpect(status().isCreated()).andExpect(result -> {
            var response = result.getResponse().getContentAsString();
            Map<String, Object> responseMap = objectMapper.readValue(response, Map.class);
            id.set(((Map<String, String>) responseMap.get("data")).get("id"));

        });

        var updateMedicineCategoryRequest = """
                {
                  "name": "Pain Relief updated",
                  "description": "Medicines used for relieving pain."
                }
                """;
        mockMvc.perform(put("/api/v1/medicineCategories/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateMedicineCategoryRequest))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    var response = result.getResponse().getContentAsString();
                    String expectedResponse = String.format("""
                        {
                           "code":null,
                           "message":null,
                           "data": {
                                "id": "%s",
                                "name": "Pain Relief updated",
                                "description": "Medicines used for relieving pain.",
                                "createdDate":"1970-01-01T00:00:00",
                                "createdBy":"user",
                                "updatedDate":"1970-01-01T00:00:00",
                                "updatedBy":"user"
                            }
                         }
                        """, id);
                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.NON_EXTENSIBLE);
                });
    }
}
