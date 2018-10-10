package com.example.demo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveUser(String login, String password, String firstName, String lastName) {
        User user = new User(login, passwordEncoder.encode(password), firstName, lastName);
        user.setEnabled(true);
        userRepository.save(user);
        UserRole userRole = new UserRole(login, "ROLE_USER");
        userRoleRepository.save(userRole);
    }
}
