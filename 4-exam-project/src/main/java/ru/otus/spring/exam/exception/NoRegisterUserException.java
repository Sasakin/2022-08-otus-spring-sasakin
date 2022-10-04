package ru.otus.spring.exam.exception;

public class NoRegisterUserException extends RuntimeException {
    public NoRegisterUserException() {
        super("User not registered");
    }
}
