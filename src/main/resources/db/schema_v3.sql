create table IF NOT EXISTS post (
    id serial primary key,
    name varchar(2000),
    description text,
    created timestamp without time zone not null default now()
);

create table IF NOT EXISTS authority (
    id serial primary key,
    authority varchar(2000)
);

create table IF NOT EXISTS users (
    id serial primary key,
    username varchar(2000),
    password  varchar(2000),
    enabled boolean,
    authority_id int references authority(id)
);

insert into post (name, description) values ('О чем этот форум?', 'Описание');
insert into post (name, description) values ('Правила форума.', 'Описание');

insert into authority (authority) values ('ROLE_ADMIN');
insert into authority (authority) values ('ROLE_USER');

insert into users (username, password, enabled, authority_id) values ('user', '$2a$10$RE8gZYX9nzjyFuD4GAoAFeQs4uhj55/lYOVVqf06L8gmfiMF2.ayG', true, 1);
