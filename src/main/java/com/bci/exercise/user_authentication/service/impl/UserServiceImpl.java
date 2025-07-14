package com.bci.exercise.user_authentication.service.impl;

import com.bci.exercise.user_authentication.dto.UserRequest;
import com.bci.exercise.user_authentication.dto.UserSignInResponse;
import com.bci.exercise.user_authentication.dto.UserSignUpResponse;
import com.bci.exercise.user_authentication.exception.ApplicationException;
import com.bci.exercise.user_authentication.exception.ErrorCodes;
import com.bci.exercise.user_authentication.mapper.PhoneMapper;
import com.bci.exercise.user_authentication.mapper.UserMapper;
import com.bci.exercise.user_authentication.model.User;
import com.bci.exercise.user_authentication.repository.UserRepository;
import com.bci.exercise.user_authentication.security.JwtConfig;
import com.bci.exercise.user_authentication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PhoneMapper phoneMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtConfig jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserSignInResponse signIn(UserRequest userRequest) throws ApplicationException {
        User byEmail = Optional.ofNullable(userRepository.findByEmail(userRequest.getEmail()))
                .orElseThrow(() -> new ApplicationException(
                        ErrorCodes.USER_NON_EXIST.code(),
                        ErrorCodes.USER_NON_EXIST.message(),
                        Timestamp.from(Instant.now())
                ));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );
        if (!authentication.isAuthenticated()) {
            throw new ApplicationException(
                    ErrorCodes.BAD_CREDENTIALS.code(),
                    ErrorCodes.BAD_CREDENTIALS.message(),
                    Timestamp.from(Instant.now())
            );
        }
        return buildUserResponse(byEmail);
    }

    public UserSignInResponse buildUserResponse(User user){
        return userMapper.convert(user);
    }

    public String getToken(UserRequest userRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getEmail(),
                        userRequest.getPassword()
                )
        );
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    @Override
    @Transactional
    public UserSignUpResponse signUp(UserRequest userRequest) throws ApplicationException {

        User byEmail = userRepository.findByEmail(userRequest.getEmail());
        if (byEmail != null) {
            throw new ApplicationException(ErrorCodes.OBJECT_ALREADY_EXISTS.code(),
                    ErrorCodes.OBJECT_ALREADY_EXISTS.message(),
                    Timestamp.from(Instant.now()));
        }

        User user = userMapper.convert(userRequest);
        user.setPassword(encoder.encode(userRequest.getPassword()));
        user.setId(UUID.randomUUID());
        user.setActive(true);
        user.setLastLogin(formatDate());

        User savedUser = userRepository.save(user);

        UserSignUpResponse userSignUpResponse = userMapper.convertFromEntity(savedUser);
        userSignUpResponse.setToken(getToken(userRequest));
        userSignUpResponse.setLastLogin(formatDate());

        return userSignUpResponse;
    }

    public String formatDate(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        return sdf.format(date);
    }
}
