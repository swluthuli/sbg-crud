package za.co.sbg.demo.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void testGenerateToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        String extractedUsername = jwtUtil.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void testValidateToken_ValidToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token, username);

        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        boolean isValid = jwtUtil.validateToken(token, "wrongUser");

        assertFalse(isValid);
    }

    @Test
    void testIsTokenExpired_NotExpired() {
        String username = "testUser";
        String token = jwtUtil.generateToken(username);

        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void testIsTokenExpired_ExpiredToken() {
        String expiredToken = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(SignatureAlgorithm.HS256, "god_is_good")
                .compact();

        assertThrows(ExpiredJwtException.class, () -> jwtUtil.extractUsername(expiredToken));
    }
}
