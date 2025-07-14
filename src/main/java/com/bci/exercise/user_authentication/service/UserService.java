package com.bci.exercise.user_authentication.service;

import com.bci.exercise.user_authentication.dto.UserRequest;
import com.bci.exercise.user_authentication.dto.UserSignInResponse;
import com.bci.exercise.user_authentication.dto.UserSignUpResponse;
import com.bci.exercise.user_authentication.exception.ApplicationException;
import com.bci.exercise.user_authentication.model.User;

public interface UserService {

    UserSignUpResponse signUp(UserRequest userRequest) throws ApplicationException;
    UserSignInResponse signIn(UserRequest userRequest) throws ApplicationException;

}
