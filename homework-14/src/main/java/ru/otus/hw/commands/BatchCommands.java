package ru.otus.hw.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class BatchCommands {

    private final JobLauncher jobLauncher;

    private final Job migrationJob;


    @ShellMethod(value = "startMigrationJob", key = "sm")
    public void startMigrationJob() throws Exception {
        JobExecution execution = jobLauncher.run(migrationJob, new JobParametersBuilder()
                .toJobParameters());

        log.info("execution: " + execution);
    }
}
