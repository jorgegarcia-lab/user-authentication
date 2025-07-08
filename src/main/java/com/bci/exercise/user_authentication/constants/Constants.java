package com.bci.exercise.user_authentication.constants;

import org.springframework.stereotype.Service;

@Service
public class Constants {
    public static final String EMAIL_ERROR_MESSAGE = "Please enter a correct email";
    public static final String PASSWORD_FORMAT_ERROR_MESSAGE = "Please enter a correct password, it should contains just 1 capital letter, 2 numbers, at least 8 characters and no more than 12.";
}
