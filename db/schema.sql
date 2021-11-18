create table IF NOT EXISTS post (
    id serial primary key,
    name varchar(2000),
    description text,
    created timestamp without time zone not null default now()
);

create table IF NOT EXISTS users (
    id serial primary key,
    username varchar(2000),
    password  varchar(2000),
    enabled boolean
);

create table IF NOT EXISTS authority (
    id serial primary key,
    authority varchar(2000)
);
