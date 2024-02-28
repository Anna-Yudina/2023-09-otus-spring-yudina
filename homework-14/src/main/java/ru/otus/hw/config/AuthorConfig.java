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
import ru.otus.hw.converters.AuthorConverter;
import ru.otus.hw.models.jpa.AuthorPostgres;
import ru.otus.hw.models.mongo.AuthorMongo;

@Configuration
@RequiredArgsConstructor
public class AuthorConfig {

    private static final int CHUNK_SIZE = 5;

    private final EntityManagerFactory entityManagerFactory;

    private final JobRepository jobRepository;

    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public ItemReader<AuthorPostgres> authorReader() {
        return new JpaCursorItemReaderBuilder<AuthorPostgres>()
                .name("authorReader")
                .queryString("select a from AuthorPostgres a")
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public ItemProcessor<AuthorPostgres, AuthorMongo> authorProcessor(AuthorConverter authorConverter) {
        return authorConverter::convert;
    }

    @Bean
    public ItemWriter<AuthorMongo> authorWriter(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<AuthorMongo>()
                .template(mongoTemplate)
                .collection("authors")
                .build();
    }

    @Bean
    public Step authorsMigrationStep(ItemReader<AuthorPostgres> authorReader, ItemWriter<AuthorMongo> authorWriter,
                           ItemProcessor<AuthorPostgres, AuthorMongo> authorProcessor) {
        return new StepBuilder("authorsMigrationStep", jobRepository)
                .<AuthorPostgres, AuthorMongo>chunk(CHUNK_SIZE, platformTransactionManager)
                .reader(authorReader)
                .processor(authorProcessor)
                .writer(authorWriter)
                .build();
    }
}
