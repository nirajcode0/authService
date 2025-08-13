package com.authService.Controller;

import com.authService.Dto.ApiResponseDto;
import com.authService.Dto.LoginDto;
import com.authService.Dto.UserDto;
import com.authService.Service.AuthService;
import com.authService.Service.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<String>> register(@RequestBody UserDto dto) {
        ApiResponseDto<String> response = authService.register(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<String>> loginCheck(@RequestBody LoginDto loginDto) {
        ApiResponseDto<String> response = new ApiResponseDto<>();

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            Authentication authenticate = authManager.authenticate(token);

            if (authenticate.isAuthenticated()) {
                String jwtToken=jwtService.generateToken(loginDto.getUsername(),authenticate.getAuthorities().iterator().next().getAuthority());
                response.setMessage("Login Sucessful");
                response.setStatus(200);
                response.setData(jwtToken);
                return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setMessage("Failed");
        response.setStatus(401);
        response.setData("Un-Authorized Access");
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }

}