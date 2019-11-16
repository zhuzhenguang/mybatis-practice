create schema eshop collate utf8_bin;

create table order_items
(
    id bigint not null
        primary key,
    product_id bigint null,
    created_at datetime default CURRENT_TIMESTAMP null
);

create table orders
(
    id bigint not null
        primary key,
    user_id bigint null,
    status varchar(255) null,
    created_at datetime default CURRENT_TIMESTAMP null
);

create table products
(
    id bigint not null
        primary key,
    category varchar(255) charset utf8 null,
    name varchar(255) charset utf8 null,
    price double null,
    storage int null,
    created_at datetime default CURRENT_TIMESTAMP null
);

create table shopping_cart_items
(
    id bigint not null
        primary key,
    count int null,
    product_id bigint null,
    created_at datetime default CURRENT_TIMESTAMP null
);

create table shopping_carts
(
    id bigint not null
        primary key,
    user_id bigint null,
    created_at datetime default CURRENT_TIMESTAMP null
);

create table users
(
    id bigint not null
        primary key,
    name varchar(255) charset utf8 null,
    password varchar(255) null,
    email varchar(255) null,
    phone varchar(11) null,
    is_login tinyint(1) default 0 null,
    created_at datetime default CURRENT_TIMESTAMP null,
    constraint name
        unique (name)
);

