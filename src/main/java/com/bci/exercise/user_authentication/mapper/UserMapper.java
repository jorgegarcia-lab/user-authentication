package com.bci.exercise.user_authentication.mapper;


import com.bci.exercise.user_authentication.dto.UserRequest;
import com.bci.exercise.user_authentication.dto.UserSignInResponse;
import com.bci.exercise.user_authentication.dto.UserSignUpResponse;
import com.bci.exercise.user_authentication.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring", imports = {Date.class}, uses = {PhoneMapper.class})
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    User convert(UserRequest userRequest);

    UserSignInResponse convert(User user);

    @Mapping(target = "isActive", source = "active")
    UserSignUpResponse convertFromEntity(User user);
}
