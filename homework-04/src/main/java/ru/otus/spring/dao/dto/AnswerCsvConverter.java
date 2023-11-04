package ru.otus.spring.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import ru.otus.spring.domain.Answer;

public class AnswerCsvConverter extends AbstractCsvConverter {
    @Override
    public Object convertToRead(String value) {
        var valueArr = value.split("%");
        return new Answer(Integer.parseInt(valueArr[0]), valueArr[1], Boolean.parseBoolean(valueArr[2]));
    }
}
