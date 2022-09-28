package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.Test;

import java.io.IOException;
import java.util.List;

public interface CsvExamService {

    List<Test> readTests() throws IOException, CsvException;

}

