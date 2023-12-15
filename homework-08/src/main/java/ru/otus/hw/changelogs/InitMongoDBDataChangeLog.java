package ru.otus.hw.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.CommentRepository;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private AuthorRepository authorRepository;

    private BookRepository bookRepository;

    private CommentRepository commentRepository;

    private Author pushkin;

    private Author tolstoy;

    private Book sampleBook;

    @ChangeSet(order = "000", id = "dropDB", author = "mongock", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "mongock", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        pushkin = repository.save(new Author("Пушкин"));
        tolstoy = repository.save(new Author("Толстой"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "mongock", runAlways = true)
    public void initBooks(BookRepository repository) {
        sampleBook = repository.save(new Book("Руслан и Людмила", pushkin, List.of("поэма", "роман")));
        repository.save(new Book("Анна Каренина", tolstoy, List.of("роман")));
    }

    @ChangeSet(order = "003", id = "initComments", author = "mongock", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment("Отлично", sampleBook));
        repository.save(new Comment("Норм", sampleBook));
    }
}
