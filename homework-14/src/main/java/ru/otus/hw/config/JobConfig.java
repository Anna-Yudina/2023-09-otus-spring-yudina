package ru.otus.hw.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@SuppressWarnings("unused")
@RequiredArgsConstructor
@Slf4j
public class JobConfig {

    public static final String MIGRATION_JOB_NAME = "migrationJob";

    private final JobRepository jobRepository;

    private final DataSource dataSource;

    private final PlatformTransactionManager platformTransactionManager;


    @Bean
    public Job migrationJob(Step authorsMigrationStep, Step genresMigrationStep, Step booksMigrationStep) {
        return new JobBuilder(MIGRATION_JOB_NAME, jobRepository)
                .start(createTempAuthorCrossIdTable())
                .next(createTempGenreCrossIdTable())
                .next(authorsMigrationStep)
                .next(genresMigrationStep)
                .next(booksMigrationStep)
                .next(dropTempAuthorCrossIdTable())
                .next(dropTempGenreCrossIdTable())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public TaskletStep createTempAuthorCrossIdTable() {
        return new StepBuilder("createTempAuthorCrossIdTable", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    new JdbcTemplate(dataSource).execute(
                            "CREATE TABLE temp_author_cross_ids" +
                                    " (id_postgres INT NOT NULL, id_mongo VARCHAR(255) NOT NULL)"
                    );
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }

    @Bean
    public TaskletStep dropTempAuthorCrossIdTable() {
        return new StepBuilder("dropTempAuthorCrossIdTable", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    new JdbcTemplate(dataSource).execute(
                            "DROP TABLE temp_author_cross_ids"
                    );
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }

    @Bean
    public TaskletStep createTempGenreCrossIdTable() {
        return new StepBuilder("createTempGenreCrossIdTable", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    new JdbcTemplate(dataSource).execute(
                            "CREATE TABLE temp_genre_cross_ids" +
                                    " (id_postgres INT NOT NULL, id_mongo VARCHAR(255) NOT NULL)"
                    );
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }

    @Bean
    public TaskletStep dropTempGenreCrossIdTable() {
        return new StepBuilder("dropTempGenreCrossIdTable", jobRepository)
                .allowStartIfComplete(true)
                .tasklet(((contribution, chunkContext) -> {
                    new JdbcTemplate(dataSource).execute(
                            "DROP TABLE temp_genre_cross_ids"
                    );
                    return RepeatStatus.FINISHED;
                }), platformTransactionManager)
                .build();
    }
}
