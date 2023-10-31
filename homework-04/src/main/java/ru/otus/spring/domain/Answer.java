package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Answer {

    private int id;

    private String answerText;

    private boolean isCorrect;

    @Override
    public String toString() {
        return answerText + ": " + (isCorrect ? "верно" : "не верно");
    }
}
