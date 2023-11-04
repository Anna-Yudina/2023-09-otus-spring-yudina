package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.TestConfig;
import ru.otus.spring.domain.TestResult;

@Service
@AllArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final LocalizedIOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        ioService.printLine("");
        ioService.printLineLocalized("ResultService.test.result");
        ioService.printFormattedLineLocalized("ResultService.student", testResult.getStudent().getFullName());
        ioService.printFormattedLineLocalized("ResultService.answered.question.count",
                testResult.getAnsweredQuestions().size());
        ioService.printFormattedLineLocalized("ResultService.right.answers.count", testResult.getRightAnswersCount());

        if (testResult.getRightAnswersCount() >= testConfig.getRightAnswersCountToPass()) {
            ioService.printLineLocalized("ResultService.success");
            return;
        }
        ioService.printLineLocalized("ResultService.unsuccess");
    }
}
