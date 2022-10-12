package ru.otus.spring.exam.service;

import com.opencsv.exceptions.CsvException;
import ru.otus.spring.exam.domain.Test;

import java.io.IOException;
import java.util.List;

public interface CsvExamService {

    List<Test> readTests() throws IOException, CsvException;

}

