package ru.otus.spring.book.mongo.services;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

    public SequenceGeneratorServiceImpl() {
    }

    public ObjectId generateSequence() {
        Date date = new Date();
        ObjectId objectIdDate = new ObjectId(date);
        return objectIdDate;

    }
}
