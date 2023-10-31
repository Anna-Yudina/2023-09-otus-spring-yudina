package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

import java.util.List;
import java.util.stream.Collectors;

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
            ioService.printLine(writeQuestion(testQuestion));
            ioService.printLineLocalized("TestService.answer.correct.number");

            String innerAnswerNumber = inputValidator.readAnswer(testQuestion.getAnswers().size());
            int innerAnswerIndex = Integer.parseInt(innerAnswerNumber) - 1;
            boolean isCorrectAnswer = testQuestion.getAnswers().get(innerAnswerIndex).isCorrect();
            testResult.applyAnswer(testQuestion, isCorrectAnswer);
            ioService.printLine("");
        }
        return testResult;
    }

    private String writeQuestion(Question question) {
        return question.getQuestionText() + "\n" +
                question.getAnswers().stream().map(answer -> answer.getAnswerText() + " - " + answer.getId())
                        .collect(Collectors.joining("\n"));
    }
}
