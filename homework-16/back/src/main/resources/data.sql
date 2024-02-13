insert into authors(full_name)
values ('Достоевский Федор Михайлович'),
       ('Толстой Лев Николаевич'),
       ('Пушкин Александр Сергеевич'),
       ('Жюль Верн');

insert into genres(name)
values ('поэма'),
       ('роман'),
       ('рассказ');

insert into books(title, author_id, genre_id)
values ('Руслан и Людмила', 3, 1),
       ('Преступление и наказание', 1, 2),
       ('Братья Карамазовы', 1, 3);

insert into comments(text, book_id)
values ('норм', 1),
       ('так себе', 2),
       ('отлично', 3);
