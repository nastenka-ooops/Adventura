create database neo_tour;
create table location
(
    id        bigint generated always as identity
        constraint location_pk
            primary key,
    location  varchar(255) not null
        constraint location_name_pk
            unique,
    country   varchar(255) not null,
    continent smallint     not null
);
create index location_continent_index
    on location (location);
create table tour
(
    id                 integer generated always as identity
        constraint tour_pk
            primary key,
    name               varchar(255)      not null,
    description        text              not null,
    booked_amount      integer           not null,
    location_id        integer           not null
        constraint tour_location_id_fk
            references location,
    recommended_season integer default 2 not null
);
create table "user"
(
    id       integer generated always as identity
        constraint user_pk
            primary key,
    username varchar(255) not null,
    password varchar(255) not null
);
create table booking
(
    id      integer generated always as identity
        constraint booking_pk
            primary key,
    phone   varchar(255) not null,
    comment text,
    tour_id integer      not null
        constraint booking_tour_id_fk
            references tour,
    user_id integer      not null
        constraint booking_user_id_fk
            references app_user
);
create table review
(
    id      integer generated always as identity
        constraint review_pk
            primary key,
    review  text    not null,
    tour_id integer not null
        constraint review_tour_id_fk
            references tour,
    user_id integer not null
        constraint review_user_id_fk
            references app_user
);
create table image
(
    id   integer generated always as identity
        constraint image_pk
            primary key,
    name varchar(255) not null,
    url  varchar(255) not null
);
create table role
(
    id   integer generated always as identity
        constraint role_pk
            primary key,
    role integer not null
);
create table tour_image
(
    tour_id  integer not null
        constraint tour_image_tour_id_fk
            references tour,
    image_id integer not null
        constraint tour_image_image_id_fk
            references image,
    constraint tour_image_pk
        primary key (tour_id, image_id)
);
create table user_image
(
    user_id  integer not null
        constraint user_id_user_id_fk
            references app_user,
    image_id integer not null
        constraint user_id_image_id_fk
            references image,
    constraint user_image_pk
        primary key (user_id, image_id)
);
create table user_role
(
    user_id integer not null
        constraint user_role_user_id_fk
            references app_user,
    role_id integer not null
        constraint user_role_role_id_fk
            references role,
    constraint user_role_pk
        primary key (user_id, role_id)
);