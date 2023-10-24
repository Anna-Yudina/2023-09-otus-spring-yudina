package ru.otus.spring.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.CsvQuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionReadException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CsvQuestionDaoTest {

    @Mock
    TestFileNameProvider fileNameProvider;

    CsvQuestionDao csvQuestionDao;


    @BeforeEach
    void init() {
        fileNameProvider = mock(TestFileNameProvider.class);
        when(fileNameProvider.getTestFileName()).thenReturn("questions-test.csv");
        csvQuestionDao = new CsvQuestionDao(fileNameProvider);
    }

    @Test
    void getUrlTest() {
        assertThat(csvQuestionDao.findAll()).isNotNull();
    }

    @Test
    void findAllTest() {
        assertThat(2).isEqualTo(csvQuestionDao.findAll().size());
    }

    @Test
    void getUrlExceptionTest() {
        when(fileNameProvider.getTestFileName()).thenReturn(null);
        assertThatExceptionOfType(QuestionReadException.class).isThrownBy(() -> csvQuestionDao.getUrl());
    }

    @Test
    void getQuestionFromFile() {
        List<Question> questions = csvQuestionDao.findAll();
        assertThat("What is the biggest bird in the world?").isEqualTo(questions.get(0).getQuestionText());
    }
}
