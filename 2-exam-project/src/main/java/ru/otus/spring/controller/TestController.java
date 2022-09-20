package ru.otus.spring.controller;

import com.opencsv.exceptions.CsvException;

import java.io.IOException;

public interface TestController {
    void prepareTest();

    void startTesting() throws Exception;

    void finishTesting();
}
