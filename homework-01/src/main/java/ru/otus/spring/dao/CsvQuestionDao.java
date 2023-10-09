package ru.otus.spring.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.otus.spring.config.TestFileNameProvider;
import ru.otus.spring.dao.dto.QuestionDto;
import ru.otus.spring.domain.Question;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @SneakyThrows
    @Override
    public List<Question> findAll() {
        URL url = getClass().getClassLoader().getResource(fileNameProvider.getTestFileName());

        List<QuestionDto> questionDtoBeans = new CsvToBeanBuilder(new FileReader(url.getFile()))
                .withType(QuestionDto.class).withSeparator(';').build().parse();
        return questionDtoBeans.stream().map(QuestionDto::toDomainObject).toList();
    }
}
