package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.feign.LibraryServiceProxi;

import java.util.List;

@Service
@Slf4j
@ConditionalOnProperty(name = "use", havingValue = "feign")
@RequiredArgsConstructor
public class AuthorService {

    private final LibraryServiceProxi feignProxy;

    public List<AuthorDto> getAllAuthors() {
        return feignProxy.getAllAuthors();
    }
}
