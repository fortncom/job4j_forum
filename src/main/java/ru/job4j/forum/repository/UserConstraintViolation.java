package ru.job4j.forum.repository;

public class UserConstraintViolation extends Exception {

    public UserConstraintViolation(String message) {
        super(message);
    }
}
