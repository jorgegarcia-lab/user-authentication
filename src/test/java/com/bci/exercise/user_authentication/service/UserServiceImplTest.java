package com.bci.exercise.user_authentication.service;

import com.bci.exercise.user_authentication.dto.UserRequest;
import com.bci.exercise.user_authentication.dto.UserSignInResponse;
import com.bci.exercise.user_authentication.dto.UserSignUpResponse;
import com.bci.exercise.user_authentication.exception.ApplicationException;
import com.bci.exercise.user_authentication.exception.ErrorCodes;
import com.bci.exercise.user_authentication.mapper.UserMapper;
import com.bci.exercise.user_authentication.model.User;
import com.bci.exercise.user_authentication.repository.UserRepository;
import com.bci.exercise.user_authentication.security.JwtConfig;
import com.bci.exercise.user_authentication.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private AuthenticationManager authenticationManager;
    @Mock private JwtConfig jwtConfig;
    @Mock private UserMapper userMapper;
    @Mock private PasswordEncoder encoder;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private UserRequest userRequest;
    private User user;

    @BeforeEach
    void setUp() {
        userRequest = UserRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        user = User.builder()
                .email("test@example.com")
                .password("encodedPassword")
                .build();
    }

    @Test
    void signIn_ShouldReturnUserResponse_WhenCredentialsAreCorrect() throws ApplicationException {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(user);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        UserSignInResponse response = UserSignInResponse.builder().build();
        when(userMapper.convert(user)).thenReturn(response);

        UserSignInResponse result = userServiceImpl.signIn(userRequest);

        assertNotNull(result);
        verify(authenticationManager).authenticate(any());
        verify(userMapper).convert(user);
    }

    @Test
    void signIn_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(null);

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> userServiceImpl.signIn(userRequest));

        assertEquals(ErrorCodes.USER_NON_EXIST.message(), exception.getErrorMessage());
    }

    @Test
    void signIn_ShouldThrowException_WhenAuthenticationFails() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(user);

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> userServiceImpl.signIn(userRequest));

        assertEquals(ErrorCodes.BAD_CREDENTIALS.message(), exception.getErrorMessage());
    }

    @Test
    void signUp_ShouldSaveUserAndReturnResponseWithToken() throws ApplicationException {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(userMapper.convert(userRequest)).thenReturn(user);
        when(encoder.encode(userRequest.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.convertFromEntity(user)).thenReturn(new UserSignUpResponse());
        when(jwtConfig.generateToken(userRequest.getEmail())).thenReturn("mocked-jwt");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");

        UserSignUpResponse response = userServiceImpl.signUp(userRequest);

        assertNotNull(response);
        assertEquals("mocked-jwt", response.getToken());
    }

    @Test
    void getToken_ShouldReturnToken_WhenAuthenticationSucceeds() {
        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = mock(UserDetails.class);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        when(jwtConfig.generateToken("test@example.com")).thenReturn("jwt-token");

        String token = userServiceImpl.getToken(userRequest);

        assertEquals("jwt-token", token);
    }

    @Test
    void formatDate_ShouldReturnFormattedString() {
        String formattedDate = userServiceImpl.formatDate();
        assertNotNull(formattedDate);
        assertTrue(formattedDate.matches("^[A-Za-z]{3} \\d{2}, \\d{4} \\d{2}:\\d{2}:\\d{2} [AP]M$"));
    }

    @Test
    void signUp_ShouldThrowException_WhenEmailExists() {
        when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(User.builder().build());

        ApplicationException exception = assertThrows(ApplicationException.class,
                () -> userServiceImpl.signUp(userRequest));

        assertEquals(ErrorCodes.OBJECT_ALREADY_EXISTS.message(), exception.getErrorMessage());
    }


}
