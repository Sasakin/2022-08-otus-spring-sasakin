package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.Test;

import java.io.IOException;
import java.util.List;

public interface CsvTestService {

    List<Test> readTests(CSVReader reader) throws IOException, CsvException;

}

