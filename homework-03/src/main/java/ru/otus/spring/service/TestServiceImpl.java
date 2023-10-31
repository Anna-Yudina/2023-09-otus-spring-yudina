package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final InputValidator inputValidator;

    private final LocalizedIOService ioService;

    private CsvQuestionDao csvQuestionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLineLocalized("TestService.answer.the.questions");

        List<Question> testQuestions = csvQuestionDao.findAll();
        TestResult testResult = new TestResult(student);

        for (Question testQuestion : testQuestions) {
            ioService.printLine(testQuestion.writeQuestion());
            ioService.printLineLocalized("TestService.answer.correct.number");

            String innerAnswerNumber = inputValidator.readAnswer(testQuestion.getAnswers().size());
            int innerAnswerIndex = Integer.parseInt(innerAnswerNumber) - 1;
            boolean isCorrectAnswer = testQuestion.getAnswers().get(innerAnswerIndex).isCorrect();
            testResult.applyAnswer(testQuestion, isCorrectAnswer);
            ioService.printLine("");
        }
        return testResult;
    }
}
