package com.esliceu.maze.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.esliceu.maze.repositorys.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import com.esliceu.maze.Utils.Utils;
import com.esliceu.maze.models.User;

@Service
public class UserService {
    @Autowired
    private Utils utils;

    @Autowired
    private UserRepository userRepository;


    public void registerUser(String name, String username, String password) throws IllegalArgumentException {
        if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("All fields are required");
        }


        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }


        if (usernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        userRepository.registerUser(name, username, utils.hashPasword(password));
    }

    public boolean usernameExists(String username) {
        try {
            userRepository.findByUsername(username);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public void loginUser(String username, String password) {
        try {
            User user = userRepository.findByUsername(username);
            if (!utils.comparePasswords(password, user.getPassword())) {
                throw new IllegalArgumentException("Username or password incorrect");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Username or password incorrect");
        }
    }

    public Long getUserIdByUsername(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        return userRepository.findByUsername(username).getId();
    }
}
