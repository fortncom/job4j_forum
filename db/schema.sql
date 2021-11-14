create table post (
    id serial primary key,
    name varchar(2000),
    description text,
    created timestamp without time zone not null default now()
);

create table users (
    id serial primary key,
    username varchar(2000),
    password  varchar(2000),
    enabled boolean
);