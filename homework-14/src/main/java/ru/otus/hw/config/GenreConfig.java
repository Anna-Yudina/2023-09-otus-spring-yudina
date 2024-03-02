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
import ru.otus.hw.converters.GenreConverter;
import ru.otus.hw.models.jpa.GenrePostgres;
import ru.otus.hw.models.mongo.GenreMongo;

@Configuration
@RequiredArgsConstructor
public class GenreConfig {

    private static final int CHUNK_SIZE = 5;

    private final EntityManagerFactory entityManagerFactory;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public ItemReader<GenrePostgres> genreReader() {
        return new JpaCursorItemReaderBuilder<GenrePostgres>()
                .name("genreReader")
                .queryString("select g from GenrePostgres g")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<GenrePostgres, GenreMongo> genreProcessor(GenreConverter genreConverter) {
        return genreConverter::convert;
    }

    @Bean
    public ItemWriter<GenreMongo> genreWriter(MongoTemplate mongoTemplate) {

        return new MongoItemWriterBuilder<GenreMongo>()
                .template(mongoTemplate)
                .collection("genres")
                .build();
    }

    @Bean
    public Step genresMigrationStep(ItemReader<GenrePostgres> genreReader, ItemWriter<GenreMongo> genreWriter,
                           ItemProcessor<GenrePostgres, GenreMongo> genreProcessor) {
        return new StepBuilder("genresMigrationStep", jobRepository)
                .<GenrePostgres, GenreMongo>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .build();
    }
}
