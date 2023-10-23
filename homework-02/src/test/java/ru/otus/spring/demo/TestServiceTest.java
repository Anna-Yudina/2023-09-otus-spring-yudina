package ru.otus.spring.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestServiceTest {

    CsvQuestionDao csvQuestionDaoMock = Mockito.mock(CsvQuestionDao.class);

    @Test
    public void getQuestionFromPropertiesNotNullTest() {
        List<Question> testQuestions = csvQuestionDaoMock.findAll();
        assertNotNull(testQuestions);
    }
}
