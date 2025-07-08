package com.bci.exercise.user_authentication.dto;


import javax.validation.constraints.*;
import com.bci.exercise.user_authentication.constants.Constants;
import com.bci.exercise.user_authentication.model.Phone;
import lombok.*;
import java.util.List;

@Data
public class UserRequest {
    private String name;
    private String email;
    @Pattern(regexp = "^(?=.*[A-Z])(?=(?:.*\\d){2,})[A-Za-z0-9]{8,12}$", message = Constants.PASSWORD_FORMAT_ERROR_MESSAGE)
    private String password;
    private List<Phone> phones;
    private String token;
}
