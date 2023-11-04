package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.TestRunnerService;

@ShellComponent
@RequiredArgsConstructor
public class ApplicationEventsCommands {

    private final TestRunnerService testRunnerService;

    @ShellMethod(key = {"test", "tt"})
    public String beginTest() {
        testRunnerService.run();
        return "Тест завершён!";
    }

    @ShellMethod(key = "hello")
    public String hello() {
        return "Добро пожаловать в программу по тестированию студентов!";
    }
}
