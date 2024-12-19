package com.esliceu.maze.repositorys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.esliceu.maze.models.User;

@Repository
public class UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void registerUser(String name, String username, String password) {
        jdbcTemplate.update("INSERT INTO users (name, username, password) VALUES (?, ?, ?)", name, username, password);
    }

    public User findByUsername(String username) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", BeanPropertyRowMapper.newInstance(User.class), username);
    }
}
