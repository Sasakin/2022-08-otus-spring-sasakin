package ru.otus.spring.book.mongo.services;

import org.bson.types.ObjectId;

public interface SequenceGeneratorService {
    ObjectId generateSequence();
}
