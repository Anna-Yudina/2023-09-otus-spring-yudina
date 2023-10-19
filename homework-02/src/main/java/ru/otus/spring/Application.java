package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.service.TestRunnerService;

public class Application {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        TestRunnerService testRunnerService = context.getBean(TestRunnerService.class);

        testRunnerService.run();

        context.close();
    }
}
