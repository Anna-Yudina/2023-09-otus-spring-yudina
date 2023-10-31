package ru.otus.spring.dao.dto;

import com.opencsv.bean.CsvBindAndSplitByPosition;
import com.opencsv.bean.CsvBindByPosition;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDto {

    @CsvBindByPosition(position = 0)
    private String questionText;

    @CsvBindAndSplitByPosition(position = 1, collectionType = ArrayList.class, elementType = Answer.class,
            converter = AnswerCsvConverter.class, splitOn = "\\|")
    private List<Answer> answers;

    public Question toDomainObject() {
        return new Question(questionText, answers);
    }
}
