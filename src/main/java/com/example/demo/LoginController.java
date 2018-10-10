package com.example.demo;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class LoginController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserRoleRepository userRoleRepository;

    public LoginController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/logowanie")
    public String login() {
        return "logowanie";
    }

    @GetMapping("/edytuj")
    public String edytuj(@RequestParam Long id, Model model) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getId());
            userDto.setLogin(user.getLogin());
            userDto.setPassword(user.getPassword());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            model.addAttribute("userDto", userDto);
            return "edytuj";
        } else {
            return "redirect:/";
        }
    }

    @PostMapping("/edytuj")
    public String edytuj(UserDto userDto) {
        Long userId = userDto.getUserId();
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.orElseThrow(() -> new RuntimeException("Nie znaleziono"));
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user);

        return "redirect:/logowanie";
    }

    @GetMapping("/ustawienia")
    public String ustawienia(Principal principal, Model model) {
        String username = principal.getName();
        User user = userRepository.findByLogin(username);
        model.addAttribute("user", user);
        return "ustawienia";

    }
}
