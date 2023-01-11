package ru.otus.spring.book.config;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import ru.otus.spring.book.jpa.domain.Author;
import ru.otus.spring.book.jpa.domain.Book;
import ru.otus.spring.book.jpa.domain.Genre;
import ru.otus.spring.book.services.CleanUpService;

import javax.persistence.EntityManagerFactory;
import java.util.List;


@Configuration
@AllArgsConstructor
public class JobConfig {
    private static final int CHUNK_SIZE = 5;
    private final Logger logger = LoggerFactory.getLogger("Batch");

    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    private final EntityManagerFactory entityManagerFactory;

    private JobBuilderFactory jobBuilderFactory;

    private StepBuilderFactory stepBuilderFactory;

    private CleanUpService cleanUpService;

    @StepScope
    @Bean
    public JpaCursorItemReader<Book> booksJpaCursorItemReaderPg() {
        return new JpaCursorItemReaderBuilder<Book>()
                .name("bookJpaCursorItemReader")
                .queryString("SELECT b FROM Book b")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<Author> authorsJpaCursorItemReaderPg() {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("authorJpaCursorItemReader")
                .queryString("SELECT a FROM Author a")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @StepScope
    @Bean
    public JpaCursorItemReader<Genre> genresJpaCursorItemReaderPg() {
        return new JpaCursorItemReaderBuilder<Genre>()
                .name("genreJpaCursorItemReader")
                .queryString("SELECT g FROM Genre g")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.book.mongo.domain.Book> mongoBookWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.book.mongo.domain.Book>().template(mongoTemplate).collection("books")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.book.mongo.domain.Author> mongoAuthorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.book.mongo.domain.Author>().template(mongoTemplate).collection("authors")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.book.mongo.domain.Genre> mongoGenreWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ru.otus.spring.book.mongo.domain.Genre>().template(mongoTemplate).collection("genres")
                .build();
    }

    @Bean
    public Job jpaCursorItemReaderJob(
            MongoItemWriter<ru.otus.spring.book.mongo.domain.Author> aWriter,
            AuthorItemProcessor aProcessor,
            MongoItemWriter<ru.otus.spring.book.mongo.domain.Genre> gWriter,
            GenreItemProcessor gProcessor,
            MongoItemWriter<ru.otus.spring.book.mongo.domain.Book> bWriter,
            BookItemProcessor bProcessor) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .start(authorsMigrationStep(aWriter, aProcessor))
                .start(genresMigrationStep(gWriter, gProcessor))
                .start(booksMigrationStep(bWriter, bProcessor))
                .next(cleanUpStep())
                .build();
    }

    @Bean
    public Step authorsMigrationStep(MongoItemWriter<ru.otus.spring.book.mongo.domain.Author> writer, AuthorItemProcessor processor) {
        return stepBuilderFactory.get("migrationAuthorsStep")
                .<Author, ru.otus.spring.book.mongo.domain.Author>chunk(CHUNK_SIZE)
                .reader(authorsJpaCursorItemReaderPg())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step genresMigrationStep(MongoItemWriter<ru.otus.spring.book.mongo.domain.Genre> writer, GenreItemProcessor processor) {
        return stepBuilderFactory.get("migrationGenresStep")
                .<Genre, ru.otus.spring.book.mongo.domain.Genre>chunk(CHUNK_SIZE)
                .reader(genresJpaCursorItemReaderPg())
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public Step booksMigrationStep(MongoItemWriter<ru.otus.spring.book.mongo.domain.Book> writer, BookItemProcessor processor) {
        return stepBuilderFactory.get("jpaCursorItemReaderStep")
                .<Book, ru.otus.spring.book.mongo.domain.Book>chunk(CHUNK_SIZE)
                .reader(booksJpaCursorItemReaderPg())
                .processor(processor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull Book o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ItemProcessListener<>() {
                    public void beforeProcess(Book o) {
                        logger.info("Начало обработки");
                    }

                    public void afterProcess(@NonNull Book o, ru.otus.spring.book.mongo.domain.Book o2) {
                        logger.info("Конец обработки");
                    }

                    public void onProcessError(@NonNull Book o, @NonNull Exception e) {
                        logger.info("Ошибка обработки");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
                .build();
    }


   @Bean
    public MethodInvokingTaskletAdapter cleanUpTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(cleanUpService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Step cleanUpStep() {
        return this.stepBuilderFactory.get("cleanUpStep")
                .tasklet(cleanUpTasklet())
                .build();
    }
}
