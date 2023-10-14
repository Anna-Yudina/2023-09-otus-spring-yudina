package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private CsvQuestionDao csvQuestionDao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        List<Question> testQuestions = csvQuestionDao.findAll();
        List<Answer> innerAnswers = new ArrayList<>();

        for (Question testQuestion : testQuestions) {
            writeQuestion(testQuestion);
            ioService.printLine("Please enter the correct answer number: ");
            int innerAnswerNumber = Integer.parseInt(readAnswer());
            innerAnswers.add(testQuestion.getAnswers().get(innerAnswerNumber - 1));
            ioService.printLine("");
        }

        ioService.printLine("Entered answers: \n" + innerAnswers);
        ioService.printLine("Number of correct answers: " + getCountRightAnswer(innerAnswers));
    }

    private void writeQuestion(Question question) {
        ioService.printLine(question.getQuestionText());
        List<Answer> testAnswers = question.getAnswers();

        for (Answer testAnswer : testAnswers) {
            ioService.printFormattedLine(testAnswer.getAnswerText() + "- " + testAnswer.getId(), true, false);
        }
    }

    private boolean validateAnswer(String string) {
        List<String> answerNumbers = Arrays.asList("1", "2", "3");
        if (string.isEmpty() || !answerNumbers.contains(string)) {
            ioService.printLine("You enter not correct number, need 1, 2 or 3, try again");
            return false;
        }
        return true;
    }

    private String readAnswer() {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        if (!validateAnswer(line)) {
            line = readAnswer();
        }
        return line;
    }

    public Long getCountRightAnswer(List<Answer> answers) {
        return answers.stream().filter(Answer::isCorrect).count();
    }
}

