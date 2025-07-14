package com.bci.exercise.user_authentication.controller;

import com.bci.exercise.user_authentication.dto.UserRequest;
import com.bci.exercise.user_authentication.dto.UserSignInResponse;
import com.bci.exercise.user_authentication.dto.UserSignUpResponse;
import com.bci.exercise.user_authentication.exception.ApplicationException;
import com.bci.exercise.user_authentication.service.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<UserSignInResponse> authenticateUser(@Valid @RequestBody UserRequest userRequest) throws ApplicationException {
        return ResponseEntity.ok(userService.signIn(userRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserSignUpResponse> registerUser(@Valid @RequestBody UserRequest userRequest) throws ApplicationException {
        return ResponseEntity.ok(userService.signUp(userRequest));
    }
}