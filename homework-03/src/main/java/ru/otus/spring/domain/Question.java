package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class Question {
    private String questionText;

    private List<Answer> answers;

    @Override
    public String toString() {
        return questionText + ": " + answers;
    }

    public String writeQuestion() {
        return questionText + "\n" +
                answers.stream().map(answer -> answer.getAnswerText() + " - " + answer.getId())
                        .collect(Collectors.joining("\n"));
    }
}
