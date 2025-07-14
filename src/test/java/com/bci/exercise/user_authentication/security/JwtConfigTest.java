package com.bci.exercise.user_authentication.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "jwt.secret=ijnsduBWDYUBWJNDUYABWFEJNASKJDNqINDFIUNAIindiufnawjnfiunwjfie",
        "jwt.expiration=1300"
})
public class JwtConfigTest {

    @Autowired
    private JwtConfig jwtConfig;

    @Test
    public void shouldGenerateValidToken() {
        String token = jwtConfig.generateToken("testuser");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void shouldExtractUsernameFromToken() {
        String token = jwtConfig.generateToken("testuser");
        String username = jwtConfig.getUsernameFromToken(token);
        assertEquals("testuser", username);
    }

    @Test
    public void shouldValidateValidToken() {
        String token = jwtConfig.generateToken("testuser");
        assertTrue(jwtConfig.validateJwtToken(token));
    }

    @Test
    public void shouldFailOnMalformedToken() {
        String invalidToken = "bad.token.value";
        assertFalse(jwtConfig.validateJwtToken(invalidToken));
    }

    @Test
    public void shouldFailOnExpiredToken() throws InterruptedException {
        String token = jwtConfig.generateToken("expireduser");
        Thread.sleep(1500);
        assertFalse(jwtConfig.validateJwtToken(token));
    }
}
