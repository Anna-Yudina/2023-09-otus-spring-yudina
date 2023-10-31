package ru.otus.spring.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.dto.QuestionDto;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionReadException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

@Repository
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Autowired
    public CsvQuestionDao(TestFileNameProvider fileNameProvider) {
        this.fileNameProvider = fileNameProvider;
    }

    @SneakyThrows
    @Override
    public List<Question> findAll() {
        List<QuestionDto> questionDtoBeans = new CsvToBeanBuilder(getFileReader())
                .withType(QuestionDto.class)
                .withSeparator(';')
                .build()
                .parse();
        return questionDtoBeans.stream().map(QuestionDto::toDomainObject).toList();
    }

    public FileReader getFileReader() {
        URL url = getUrl();
        FileReader fileReader;
        try {
            fileReader = new FileReader(url.getFile());
        } catch (FileNotFoundException e) {
            throw new QuestionReadException("File not found", e);
        }
        return fileReader;
    }

    public URL getUrl() {
        URL url;
        try {
            url = getClass().getClassLoader().getResource(fileNameProvider.getTestFileName());
        } catch (NullPointerException e) {
            throw new QuestionReadException("Url is null", e);
        }
        return url;
    }
}
