package com.bci.exercise.user_authentication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSignInResponse {

    private UUID id;
    private String createdAt;
    private String lastLogin;
    private String token;
    @JsonProperty("isActive")
    private boolean isActive;
    private String name;
    private String password;
    private String email;
    private List<UserPhones> phones;
}
