insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3'), ('Author_4'), ('Author_5'), ('Author_6');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'), ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3),
       ('BookTitle_4', 4), ('BookTitle_5', 5), ('BookTitle_6', 6);

insert into comments(text, book_id)
values ('comment_1', 1), ('comment_2', 1), ('comment_3', 1),
       ('comment_4', 2), ('comment_5', 2), ('comment_6', 2),
       ('comment_7', 3), ('comment_8', 3), ('comment_9', 3),
       ('comment_10', 4), ('comment_11', 4), ('comment_12', 4),
       ('comment_13', 5), ('comment_14', 5), ('comment_15', 5),
       ('comment_16', 6), ('comment_17', 6), ('comment_18', 6);

insert into book_genre(book_id, genre_id)
values (1, 1), (1, 2), (2, 2),
       (2, 3), (3, 4), (4, 4),
       (5, 5), (5, 6), (6, 6);
