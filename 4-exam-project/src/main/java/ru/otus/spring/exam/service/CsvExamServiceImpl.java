package ru.otus.spring.exam.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.exam.converter.StringArrTestConverter;
import ru.otus.spring.exam.domain.Test;
import ru.otus.spring.exam.tools.ExamCsvReader;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CsvExamServiceImpl implements CsvExamService {

    private final StringArrTestConverter converter;

    private ExamCsvReader reader;

    @Override
    public List<Test> readTests() throws IOException, CsvException {
        List<String[]> r = reader.getReader().readAll();
        return r.stream().map(data -> converter.convert(data)).collect(Collectors.toList());
    }
}
