package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.feign.LibraryServiceProxi;

import java.util.List;

@Service
@Slf4j
@ConditionalOnProperty(name = "use", havingValue = "feign")
@RequiredArgsConstructor
public class BookService {

    private final LibraryServiceProxi feignProxy;

    public List<BookDto> getAllBooks() {
        return feignProxy.getAllBooks();
    }
}
