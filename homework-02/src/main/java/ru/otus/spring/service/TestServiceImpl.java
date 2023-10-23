package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.TestResult;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private CsvQuestionDao csvQuestionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> testQuestions = csvQuestionDao.findAll();
        List<Answer> innerAnswers = new ArrayList<>();
        var testResult = new TestResult(student);

        for (Question testQuestion : testQuestions) {
            testResult.getAnsweredQuestions().add(testQuestion);
            writeQuestion(testQuestion);
            ioService.printLine("Please enter the correct answer number: ");

            int innerAnswerNumber = readAnswer(testQuestion.getAnswers().size());
            innerAnswers.add(testQuestion.getAnswers().get(innerAnswerNumber - 1));
            ioService.printLine("");
        }
        testResult.setRightAnswersCount(getCountRightAnswer(innerAnswers));
        return testResult;
    }

    private void writeQuestion(Question question) {
        ioService.printLine(question.getQuestionText());
        List<Answer> testAnswers = question.getAnswers();

        for (Answer testAnswer : testAnswers) {
            ioService.printFormattedLine(testAnswer.getAnswerText() + "- " + testAnswer.getId());
        }
    }

    public int getCountRightAnswer(List<Answer> answers) {
        return (int) answers.stream().filter(Answer::isCorrect).count();
    }

    public int readAnswer(int maxAnswerNumber) {
        int answerNumber;
        try {
            answerNumber = ioService.readString(maxAnswerNumber);
        } catch (IllegalArgumentException e) {
            ioService.printLine("You enter not correct number, need range of numbers from 1 to " +
                     maxAnswerNumber + ". Please, try again");
            answerNumber = readAnswer(maxAnswerNumber);
        }
        return answerNumber;
    }
}
