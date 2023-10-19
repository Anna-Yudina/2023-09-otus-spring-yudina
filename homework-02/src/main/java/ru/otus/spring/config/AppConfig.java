package ru.otus.spring.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Data
@Configuration
@ComponentScan("ru.otus.spring")
@PropertySource("classpath:application.properties")
public class AppConfig implements TestFileNameProvider, TestConfig {

    private String testFileName;

    private int rightAnswersCountToPass;

    public AppConfig(@Value("${test.fileName}") String testFileName,
                     @Value("${test.rightAnswersCountToPass}") int rightAnswersCountToPass) {
        this.testFileName = testFileName;
        this.rightAnswersCountToPass = rightAnswersCountToPass;
    }

    @Override
    public String getTestFileName() {
        return testFileName;
    }

    @Override
    public int getRightAnswersCountToPass() {
        return rightAnswersCountToPass;
    }
}
