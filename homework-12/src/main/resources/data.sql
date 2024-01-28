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

insert into users(username, password, enabled)
values ('yudina', '$2a$12$4SM5LQ/U8sLpEO3YSZunSOxdiz9EtBuuWdt0X2Oz4MOPGfO0rnGn.', 1),
       ('admin', '$2a$12$9F4hbOb88nWRANef6vHe0OAU3pxjHVd1Fa73fRL.MKHHG0H7VMniO', 1);

