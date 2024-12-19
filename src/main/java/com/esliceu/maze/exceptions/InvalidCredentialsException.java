package com.esliceu.maze.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Username or password is incorrect");
    }
}
