package ru.otus.spring.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.service.TestServiceImpl;
import java.util.ArrayList;
import java.util.List;

public class TestServiceImplTest {

    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
    TestServiceImpl testServiceImpl = context.getBean(TestServiceImpl.class);

    @Test
    void getCountRightAnswer(){
        List<Answer> answers = new ArrayList<>();

        Answer answer1 = new Answer(1, "one", true);
        Answer answer2 = new Answer(2, "two", true);
        Answer answer3 = new Answer(3, "three", false);
        Answer answer4 = new Answer(4, "four", true);
        Answer answer5 = new Answer(5, "five", false);

        answers.add(answer1);
        answers.add(answer2);
        answers.add(answer3);
        answers.add(answer4);
        answers.add(answer5);

        Long expectedCount = 3L;
        Long actualCount = testServiceImpl.getCountRightAnswer(answers);
        Assertions.assertEquals(expectedCount, actualCount);
    }
}
