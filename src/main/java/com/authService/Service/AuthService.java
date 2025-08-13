package com.authService.Service;

import com.authService.Dto.ApiResponseDto;
import com.authService.Dto.UserDto;
import com.authService.Entity.User;
import com.authService.Repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ApiResponseDto<String> register(UserDto dto)
    {
        if(userRepository.existsByUsername(dto.getUsername())) {
            ApiResponseDto<String> response = new ApiResponseDto<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with username exists");
            return response;
        }
        if(userRepository.existsByEmail(dto.getEmail())) {
            ApiResponseDto<String> response = new ApiResponseDto<>();
            response.setMessage("Registration Failed");
            response.setStatus(500);
            response.setData("User with Email Id exists");
            return response;
        }

        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.save(user);

        ApiResponseDto<String> response = new ApiResponseDto<>();
        response.setMessage("Registration Done");
        response.setStatus(201);
        response.setData("User is registered");

        return response;
    }
}
