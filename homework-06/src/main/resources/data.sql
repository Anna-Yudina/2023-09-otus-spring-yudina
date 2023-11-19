insert into authors(full_name)
values ('Достоевский Федор Михайлович'),
       ('Толстой Лев Николаевич'),
       ('Пушкин Александр Сергеевич'),
       ('Жюль Верн');

insert into genres(name)
values ('поэма'),
       ('роман'),
       ('рассказ');

insert into books(title, author_id)
values ('Руслан и Людмила', 3),
       ('Преступление и наказание', 1),
       ('Братья Карамазовы', 1);

insert into comments(text, book_id)
values ('норм', 1),
       ('так себе', 2),
       ('отлично', 3);

insert into book_genre(book_id, genre_id)
values (1, 1),
       (2, 2),
       (3, 2)
