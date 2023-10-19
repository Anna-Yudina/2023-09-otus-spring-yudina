package ru.otus.spring.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TestResult {

    private final Student student;

    private final List<Question> answeredQuestions;

    private int rightAnswersCount;

    public TestResult(Student student) {
        this.student = student;
        this.answeredQuestions = new ArrayList<>();
    }
}
