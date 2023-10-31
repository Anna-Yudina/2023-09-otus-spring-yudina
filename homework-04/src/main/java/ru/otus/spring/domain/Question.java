package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Question {
    private String questionText;

    private List<Answer> answers;

    @Override
    public String toString() {
        return questionText + ": " + answers;
    }
}
