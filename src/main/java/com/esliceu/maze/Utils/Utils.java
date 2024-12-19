package com.esliceu.maze.Utils;

import org.springframework.stereotype.Controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class Utils {
    public String hashPasword (String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public boolean comparePasswords(String rawPassword, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, hashedPassword);
    }
}
