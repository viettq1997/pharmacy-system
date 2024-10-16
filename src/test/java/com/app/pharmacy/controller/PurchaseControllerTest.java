//package com.app.pharmacy.controller;
//
//import com.app.pharmacy.config.TestSecurityConfig;
//import com.app.pharmacy.service.KeycloakAdminService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.skyscreamer.jsonassert.JSONAssert;
//import org.skyscreamer.jsonassert.JSONCompareMode;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import java.time.Clock;
//import java.time.Instant;
//import java.time.ZoneOffset;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("test")
//@Import(TestSecurityConfig.class)
//public class PurchaseControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private Clock clock;
//
//    @MockBean
//    private KeycloakAdminService keycloakAdminService;
//
//    @MockBean
//    private JwtDecoder jwtDecoder;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @DisplayName("Create a Purchase: "
//            + "givenCreatePurchaseRequest"
//            + "_whenCallCreatePurchaseApi"
//            + "_thenReturnPurchaseResponse")
//    @Test
//    @WithMockUser
//    public void givenCreatePurchaseRequest_whenCallCreatePurchaseApi_thenReturnPurchaseResponse() throws Exception {
//        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
//        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
//
//        var createPurchaseRequest = """
//                {
//                  "medicineId": "MED12345",
//                  "supplierId": "SUP67890",
//                  "quantity": 100,
//                  "cost": 19.99
//                }
//                """;
//        createPurchase(createPurchaseRequest)
//                .andExpect(status().isCreated())
//                .andExpect(result -> {
//                    var response = result.getResponse().getContentAsString();
//                    String expectedResponse = """
//                            {
//                               "data": {
//                                  "medicineId": "MED12345",
//                                  "supplierId": "SUP67890",
//                                  "quantity": 100,
//                                  "cost": 19.99,
//                                  "createdDate": "1970-01-01T00:00:00",
//                                  "createdBy": "user"
//                                }
//                             }
//                            """;
//                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
//                });
//    }
//
//    @DisplayName("Get Purchase: "
//            + "givenGetPurchaseRequest"
//            + "_whenCallGetPurchaseApi"
//            + "_thenReturnPurchasesResponse")
//    @Test
//    @WithMockUser
//    public void givenGetPurchaseRequest_whenCallGetPurchaseApi_thenReturnPurchasesResponse() throws Exception {
//        Mockito.when(clock.instant()).thenReturn(Instant.ofEpochMilli(0));
//        Mockito.when(clock.getZone()).thenReturn(ZoneOffset.UTC);
//
//        var createPurchaseRequest = """
//                {
//                  "medicineId": "MED12345",
//                  "supplierId": "SUP67890",
//                  "quantity": 100,
//                  "cost": 19.99
//                }
//                """;
//        createPurchase(createPurchaseRequest).andExpect(status().isCreated());
//
//        mockMvc.perform(get("/api/v1/purchases", "ABC Purchases test")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(result -> {
//                    var response = result.getResponse().getContentAsString();
//                    String expectedResponse = """
//                            {
//                               "data": {
//                                 "content": [
//                                   {
//                                      "name": "ABC Purchases test",
//                                      "address": "1234 Main Street, Springfield",
//                                      "phoneNo": "123-456-7890",
//                                      "mail": "abc@purchases.com",
//                                      "createdDate": "1970-01-01T00:00:00",
//                                      "createdBy": "user"
//                                    }
//                                 ],
//                                 "size": 10,
//                                 "number": 0,
//                                 "totalElement": 1
//                               }
//                             }
//                            """;
//                    JSONAssert.assertEquals(expectedResponse, response, JSONCompareMode.LENIENT);
//                });
//    }
//
//    private ResultActions createPurchase(String createPurchaseRequest) throws Exception {
//        return mockMvc.perform(post("/api/v1/purchases")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(createPurchaseRequest));
//    }
//}
