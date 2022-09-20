package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.converter.StringArrTestConverter;
import ru.otus.spring.domain.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CsvTestServiceImpl implements CsvTestService {

    private StringArrTestConverter converter;

    @Override
    public List<Test> readTests(CSVReader reader) throws IOException, CsvException {
        List<String[]> r = reader.readAll();
        return r.stream().map(data -> converter.convert(data)).collect(Collectors.toList());
    }
}
