package za.co.sbg.demo.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.co.sbg.demo.domain.request.LoginRequest;
import za.co.sbg.demo.handler.exception.ConstraintViolationException;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;
import za.co.sbg.demo.persistence.entity.User;
import za.co.sbg.demo.persistence.repository.UserRepository;
import za.co.sbg.demo.security.JwtUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin_Success() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testUser");
        loginRequest.setPassword("password");

        User user = User.builder().name("testUser").password("password").build();
        when(userRepository.findByUsername("testUser", "password")).thenReturn(user);
        when(jwtUtil.generateToken("testUser")).thenReturn("validToken");

        // Act
        String token = authService.login(loginRequest);

        // Assert
        assertNotNull(token);
        assertEquals("validToken", token);
        verify(userRepository, times(1)).findByUsername("testUser", "password");
        verify(jwtUtil, times(1)).generateToken("testUser");
    }

    @Test
    void testLogin_ConstraintViolationException() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest(); // Invalid request (no username/password)

        // Act & Assert
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> authService.login(loginRequest));

        assertEquals("Please provide a valid username/password", exception.getMessage());
        verify(userRepository, never()).findByUsername(anyString(), anyString());
        verify(jwtUtil, never()).generateToken(anyString());
    }

    @Test
    void testLogin_UserNotFound() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("unknownUser");
        loginRequest.setPassword("password");

        when(userRepository.findByUsername("unknownUser", "password")).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> authService.login(loginRequest));

        assertEquals("User not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername("unknownUser", "password");
        verify(jwtUtil, never()).generateToken(anyString());
    }
}
