package ru.otus.spring.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.dto.QuestionDto;
import ru.otus.spring.domain.Question;
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
        URL url = getClass().getClassLoader().getResource(fileNameProvider.getTestFileName());

        List<QuestionDto> questionDtoBeans = new CsvToBeanBuilder(new FileReader(url.getFile()))
                .withType(QuestionDto.class).withSeparator(';').build().parse();
        return questionDtoBeans.stream().map(QuestionDto::toDomainObject).toList();
    }
}
