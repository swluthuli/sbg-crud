package za.co.sbg.demo.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthFilterTest {

    @InjectMocks
    private AuthFilter authFilter;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private ContainerRequestContext requestContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFilter_NonDeleteMethod() {
        when(requestContext.getMethod()).thenReturn("GET");

        authFilter.filter(requestContext);

        verify(requestContext, never()).getHeaders();
    }

    @Test
    void testFilter_NoAuthorizationHeader() {
        when(requestContext.getMethod()).thenReturn("DELETE");
        when(requestContext.getHeaders()).thenReturn(new MultivaluedHashMap<>());

        authFilter.filter(requestContext);

        verify(requestContext, times(1)).abortWith(any(Response.class));
    }

    @Test
    void testFilter_InvalidAuthorizationHeader() {
        when(requestContext.getMethod()).thenReturn("DELETE");
        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Authorization", "InvalidToken");
        when(requestContext.getHeaders()).thenReturn(headers);

        authFilter.filter(requestContext);

        verify(requestContext, times(1)).abortWith(any(Response.class));
    }

    @Test
    void testFilter_ValidToken() {
        when(requestContext.getMethod()).thenReturn("DELETE");
        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Authorization", "Bearer validToken");
        when(requestContext.getHeaders()).thenReturn(headers);

        when(jwtUtil.extractUsername("validToken")).thenReturn("user");
        when(jwtUtil.validateToken("validToken", "user")).thenReturn(true);

        authFilter.filter(requestContext);

        verify(requestContext, never()).abortWith(any(Response.class)); // No abort, valid token
    }

    @Test
    void testFilter_InvalidToken() {
        when(requestContext.getMethod()).thenReturn("DELETE");
        MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
        headers.add("Authorization", "Bearer invalidToken");
        when(requestContext.getHeaders()).thenReturn(headers);

        when(jwtUtil.extractUsername("invalidToken")).thenReturn(null);

        authFilter.filter(requestContext);

        verify(requestContext, times(1)).abortWith(any(Response.class)); // Abort with unauthorized response
    }
}
