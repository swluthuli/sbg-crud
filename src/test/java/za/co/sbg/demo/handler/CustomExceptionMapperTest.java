package za.co.sbg.demo.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.co.sbg.demo.domain.ErrorResponse;
import za.co.sbg.demo.handler.exception.ResourceNotFoundException;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionMapperTest {

    private CustomExceptionMapper customExceptionMapper;

    @BeforeEach
    void setUp() {
        customExceptionMapper = new CustomExceptionMapper();
    }

    @Test
    void testResourceNotFoundException() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");

        // Act
        Response response = customExceptionMapper.toResponse(exception);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("Not Found", errorResponse.getError());
        assertEquals("Resource not found", errorResponse.getMessage());
    }

    @Test
    void testIllegalArgumentException() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");

        // Act
        Response response = customExceptionMapper.toResponse(exception);

        // Assert
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("Validation error", errorResponse.getError());
        assertEquals("Invalid argument", errorResponse.getMessage());
    }

    @Test
    void testUnhandledException() {
        // Arrange
        RuntimeException exception = new RuntimeException("Something went wrong");

        // Act
        Response response = customExceptionMapper.toResponse(exception);

        // Assert
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());

        ErrorResponse errorResponse = (ErrorResponse) response.getEntity();
        assertEquals("Internal Server Error", errorResponse.getError());
        assertEquals("An unexpected error", errorResponse.getMessage());
    }
}
