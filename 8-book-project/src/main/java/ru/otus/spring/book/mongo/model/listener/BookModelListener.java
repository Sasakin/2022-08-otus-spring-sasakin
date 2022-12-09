package ru.otus.spring.book.mongo.model.listener;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.services.SequenceGeneratorService;

@Component
public class BookModelListener extends AbstractMongoEventListener<Book> {

    private SequenceGeneratorService sequenceGeneratorService;

    public BookModelListener(SequenceGeneratorService sequenceGeneratorService) {
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Book> event) {
        if (event.getSource().getId() < 1) {
            event.getSource().setId(sequenceGeneratorService.generateSequence(Book.SEQUENCE_NAME));
        }
    }
}
