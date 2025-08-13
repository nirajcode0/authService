package com.authService.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcomeForAdmin() {
        return "Welcome admin";
    }
}
