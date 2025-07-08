package com.bci.exercise.user_authentication.mapper;

import com.bci.exercise.user_authentication.dto.UserPhones;
import com.bci.exercise.user_authentication.model.Phone;
import org.mapstruct.Mapper;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Date.class})
public interface PhoneMapper {

    UserPhones convert(Phone phone);
    List<UserPhones> convert(List<Phone> phone);
}
