package ru.otus.hw.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import ru.otus.hw.converters.BookConverter;
import ru.otus.hw.models.jpa.BookPostgres;
import ru.otus.hw.models.mongo.BookMongo;

@Configuration
@RequiredArgsConstructor
public class BookConfig {

    private static final int CHUNK_SIZE = 5;

    private final EntityManagerFactory entityManagerFactory;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public ItemReader<BookPostgres> bookReader() {
        return new JpaCursorItemReaderBuilder<BookPostgres>()
                .name("bookReader")
                .queryString("select b from BookPostgres b")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<BookPostgres, BookMongo> bookProcessor(BookConverter bookConverter) {
        return bookConverter::convert;
    }

    @Bean
    public ItemWriter<BookMongo> bookWriter(MongoTemplate mongoTemplate) {

        return new MongoItemWriterBuilder<BookMongo>()
                .template(mongoTemplate)
                .collection("books")
                .build();
    }

    @Bean
    public Step booksMigrationStep(ItemReader<BookPostgres> bookReader, ItemWriter<BookMongo> bookWriter,
                                    ItemProcessor<BookPostgres, BookMongo> bookProcessor) {
        return new StepBuilder("booksMigrationStep", jobRepository)
                .<BookPostgres, BookMongo>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .build();
    }
}
