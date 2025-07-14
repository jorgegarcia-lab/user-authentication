package com.bci.exercise.user_authentication;

import com.bci.exercise.user_authentication.security.JwtConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtConfigTest {

    @InjectMocks
    private JwtConfig jwtConfig;

    @Mock
    private JwtConfig jwtConfigMock;

    private String token;

    private final String mockKey = "eWrdftYsdwaswgwqerwsde767eggtsfdtegrhtyfudjsloirj54ndhdhfhhhshs9534265sikoj";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        Field secretField = JwtConfig.class.getDeclaredField("jwtSecret");
        secretField.setAccessible(true);
        secretField.set(jwtConfig, mockKey);

        Field expirationField = JwtConfig.class.getDeclaredField("jwtExpirationMs");
        expirationField.setAccessible(true);
        expirationField.set(jwtConfig, 3600000);

        jwtConfig.init();
    }

    @Test
    void generateToken_ShouldReturnValidToken_WhenGivenValidUsername() {
        String username = "testUser";
        String generatedToken = jwtConfig.generateToken(username);
        assertNotNull(generatedToken);
    }

    @Test
    void getUsernameFromToken_ShouldReturnUsername_WhenGivenValidToken() {
        String username = "testUser";
        token = jwtConfig.generateToken(username);
        String extractedUsername = jwtConfig.getUsernameFromToken(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateJwtToken_ShouldReturnTrue_WhenTokenIsValid() {
        String username = "testUser";
        token = jwtConfig.generateToken(username);
        boolean isValid = jwtConfig.validateJwtToken(token);
        assertTrue(isValid);
    }

    @Test
    void validateJwtToken_ShouldReturnFalse_WhenTokenIsMalformed() {
        String malformedToken = "malformedToken";
        boolean isValid = jwtConfig.validateJwtToken(malformedToken);
        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_ShouldReturnFalse_WhenTokenIsUnsupported() {
        String unsupportedToken = "unsupportedToken";
        boolean isValid = jwtConfig.validateJwtToken(unsupportedToken);
        assertFalse(isValid);
    }

    @Test
    void validateJwtToken_ShouldReturnFalse_WhenTokenIsEmpty() {
        String emptyToken = "";
        boolean isValid = jwtConfig.validateJwtToken(emptyToken);
        assertFalse(isValid);
    }
}

