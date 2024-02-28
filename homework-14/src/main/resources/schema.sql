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

create table books
(
    id        bigserial,
    title     varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id  bigint references genres (genre_id) on delete cascade,
    primary key (id)
);