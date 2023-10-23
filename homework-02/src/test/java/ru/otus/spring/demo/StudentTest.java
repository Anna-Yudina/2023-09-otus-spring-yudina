package ru.otus.spring.demo;

import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    Student student = new Student("Ivan", "Ivanov");

    @Test
    public void getFullNameTest(){
        String fullName = "Ivan Ivanov";
        assertThat(fullName).isEqualTo(student.getFullName());
    }
}
