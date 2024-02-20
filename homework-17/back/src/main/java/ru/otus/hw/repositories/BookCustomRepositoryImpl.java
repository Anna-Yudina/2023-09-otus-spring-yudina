package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.mapping.BookMapper;
import ru.otus.hw.models.Book;

@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final DatabaseClient client;

    @Override
    public Flux<Book> findAllBooks() {
        System.out.println("зашел в метод findAllBooks");
        String query = "select books.id, books.title, author_id, a.full_name, books.genre_id, g.name from books " +
                " join authors a on a.id = books.author_id join genres g on g.genre_id = books.genre_id";

        return client.sql(query)
                .map(new BookMapper()::apply)
                .all();
    }
}
