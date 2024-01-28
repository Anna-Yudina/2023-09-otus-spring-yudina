create table authors
(
    id        bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres
(
    genre_id   bigserial,
    name varchar(255),
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
    genre_id  bigint references genres (genre_id) on delete cascade,
    primary key (id)
);

alter table comments
    add column book_id bigint;

create table users
    (
        id bigserial,
        username varchar(15),
        password varchar(100),
        enabled smallint,
        primary key (id)
);
