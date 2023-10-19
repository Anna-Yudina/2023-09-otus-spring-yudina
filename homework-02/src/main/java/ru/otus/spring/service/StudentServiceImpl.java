package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    @Override
    public Student determineCurrentStudent() {
        String name = ioService.readStringWithPrompt("Please, enter your name: ");
        String surname = ioService.readStringWithPrompt("Please, enter your surname: ");
        return new Student(name, surname);
    }
}
