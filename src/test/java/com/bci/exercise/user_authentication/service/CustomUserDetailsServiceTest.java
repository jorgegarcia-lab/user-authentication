package com.bci.exercise.user_authentication.service;

import com.bci.exercise.user_authentication.exception.ErrorCodes;
import com.bci.exercise.user_authentication.model.User;
import com.bci.exercise.user_authentication.repository.UserRepository;
import com.bci.exercise.user_authentication.service.impl.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private final String validEmail = "test@example.com";
    private final String invalidEmail = "nonexistent@example.com";
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .email(validEmail)
                .password("encodedPassword")
                .build();
    }

    @Test
    void loadUserByUsername_ShouldReturnUserDetails_WhenUserExists() {
        when(userRepository.findByEmail(validEmail)).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(validEmail);

        assertNotNull(userDetails);
        assertEquals(validEmail, userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {
        when(userRepository.findByEmail(invalidEmail)).thenReturn(null);

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(invalidEmail));

        assertEquals(ErrorCodes.USER_NON_EXIST.message(), exception.getMessage());
    }
}

