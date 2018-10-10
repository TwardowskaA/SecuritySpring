package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/rejestracja")
    public String register() {
        return "rejestracja";
    }

    @PostMapping("/rejestracja")
    public String register(@RequestParam String login,
                           @RequestParam String password,
                          @RequestParam String firstName,
                           @RequestParam String lastName) {
        userService.saveUser(login, password,firstName, lastName);
        return "redirect:/logowanie";
    }
}
