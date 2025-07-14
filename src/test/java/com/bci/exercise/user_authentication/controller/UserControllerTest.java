package com.bci.exercise.user_authentication.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bci.exercise.user_authentication.dto.UserRequest;
import com.bci.exercise.user_authentication.dto.UserSignInResponse;
import com.bci.exercise.user_authentication.dto.UserSignUpResponse;
import com.bci.exercise.user_authentication.exception.ApplicationException;
import com.bci.exercise.user_authentication.exception.ErrorCodes;
import com.bci.exercise.user_authentication.model.User;
import com.bci.exercise.user_authentication.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void authenticateUser_ShouldReturnUserSignInResponse() throws Exception {
        UserRequest request = UserRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        UserSignInResponse response = UserSignInResponse.builder()
                .token("dummyToken")
                .build();

        when(userService.signIn(request)).thenReturn(response);

        ResponseEntity<UserSignInResponse> result = userController.authenticateUser(request);

        assertThat(result.getBody()).isEqualTo(response);
        verify(userService).signIn(request);
    }

    @Test
    public void registerUser_ShouldReturnUserSignUpResponse_WhenEmailNotExists() throws Exception {
        UserRequest request = UserRequest.builder()
                .email("newuser@example.com")
                .password("password")
                .build();

        UserSignUpResponse signUpResponse = UserSignUpResponse.builder()
                .id(UUID.randomUUID())
                .isActive(true)
                .build();

        when(userService.signUp(request)).thenReturn(signUpResponse);

        ResponseEntity<UserSignUpResponse> result = userController.registerUser(request);

        assertThat(result.getBody()).isEqualTo(signUpResponse);
        verify(userService).signUp(request);
    }
}
