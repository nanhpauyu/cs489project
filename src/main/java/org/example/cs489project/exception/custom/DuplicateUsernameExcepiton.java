package org.example.cs489project.exception.custom;

public class DuplicateUsernameExcepiton extends RuntimeException {
    public DuplicateUsernameExcepiton(String message) {
        super(message);
    }
}
