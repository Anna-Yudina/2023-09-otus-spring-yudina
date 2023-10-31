package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final LocalizedIOService ioService;

    @Override
    public Student determineCurrentStudent() {
        String name = ioService.readStringWithPromptLocalized("StudentService.input.first.name");
        String surname = ioService.readStringWithPromptLocalized("StudentService.input.last.name");
        return new Student(name, surname);
    }
}
