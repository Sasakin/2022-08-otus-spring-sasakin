package ru.otus.spring.exam.exception;

public class TestNotPassedException extends RuntimeException{
    public TestNotPassedException() {
        super("Test not passed");
    }
}
