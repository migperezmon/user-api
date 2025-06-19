package cl.ntt.userapi.user_api.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.ntt.userapi.user_api.dto.LoginRequest;
import cl.ntt.userapi.user_api.dto.UserResponse;
import cl.ntt.userapi.user_api.services.interfaces.UserService;

class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testLogin_Success() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        UserResponse userResponse = new UserResponse();
        userResponse.setId("1");
        userResponse.setCreado("2023-10-01T00:00:00Z");
        userResponse.setModificado("2023-10-01T00:00:00Z");
        userResponse.setUltimoLogin("2023-10-01T00:00:00Z");
        userResponse.setToken("jwt-token");
        userResponse.setActivo(true);

        when(userService.login(any(LoginRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.token").value("jwt-token"))
                .andExpect(jsonPath("$.is_active").value(true))
                .andExpect(jsonPath("$.created").value("2023-10-01T00:00:00Z"))
                .andExpect(jsonPath("$.modified").value("2023-10-01T00:00:00Z"))
                .andExpect(jsonPath("$.last_login").value("2023-10-01T00:00:00Z"));
    }

    @Test
    void testLogin_InvalidRequest() throws Exception {
        LoginRequest loginRequest = new LoginRequest();

        mockMvc.perform(post("/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }
}