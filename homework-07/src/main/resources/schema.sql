create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres
(
    genre_id bigserial,
    name     varchar(255),
    primary key (genre_id)
);

create table comments
(
    id   bigserial,
    text varchar(255),
    primary key (id)
);

create table books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

alter table comments
    add column book_id bigint;

create table book_genre
(
    book_id  bigint not null,
    genre_id bigint not null
)