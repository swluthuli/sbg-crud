package za.co.sbg.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.domain.request.LoginRequest;
import za.co.sbg.demo.service.AuthService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController; // Controller we are testing

    @Mock
    private AuthService authService; // Mocked service

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("testPassword");

        String expectedToken = "mockedJwtToken";

        // Mock the behavior of AuthService
        when(authService.login(loginRequest)).thenReturn(expectedToken);

        // Act
        Response response = authController.login(loginRequest);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedToken, response.getEntity());
    }

    @Test
    void testLogin_Failure() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("invalidUser");
        loginRequest.setPassword("invalidPassword");

        // Mock the behavior of AuthService to return null for invalid login
        when(authService.login(loginRequest)).thenReturn(null);

        // Act
        Response response = authController.login(loginRequest);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus()); // The controller still returns OK
        assertEquals(null, response.getEntity()); // But the entity (token) is null
    }
}
