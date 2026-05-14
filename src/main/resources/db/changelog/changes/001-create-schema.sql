--liquibase formatted sql

--changeset cj:001-create-schema

CREATE SEQUENCE bookstore.author_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE bookstore.book_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE bookstore.address_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE bookstore.customer_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE bookstore.book_order_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE bookstore.order_item_id_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE bookstore.author (
    author_id    BIGINT PRIMARY KEY DEFAULT nextval('bookstore.author_id_seq'),
    author_name  VARCHAR(50)   NOT NULL,
    active       BOOLEAN       NOT NULL DEFAULT TRUE
);

CREATE TABLE bookstore.book (
    book_id     BIGINT PRIMARY KEY DEFAULT nextval('bookstore.book_id_seq'),
    book_title  VARCHAR(255)  NOT NULL,
    isbn        VARCHAR(13)   NOT NULL UNIQUE,
    price       NUMERIC(10,2) NOT NULL,
    active      BOOLEAN       NOT NULL DEFAULT TRUE,
    author_id   BIGINT        NOT NULL REFERENCES bookstore.author(author_id)
);

CREATE TABLE bookstore.address (
    address_id  BIGINT PRIMARY KEY DEFAULT nextval('bookstore.address_id_seq'),
    street      VARCHAR(50)  NOT NULL,
    city        VARCHAR(20)  NOT NULL,
    state       VARCHAR(20)  NOT NULL,
    zip         VARCHAR(15)  NOT NULL
);

CREATE TABLE bookstore.customer (
    customer_id          BIGINT PRIMARY KEY DEFAULT nextval('bookstore.customer_id_seq'),
    customer_first_name  VARCHAR(20)  NOT NULL,
    customer_last_name   VARCHAR(20)  NOT NULL,
    customer_email       VARCHAR(50)  NOT NULL UNIQUE,
    billing_address_id   BIGINT REFERENCES bookstore.address(address_id),
    shipping_address_id  BIGINT REFERENCES bookstore.address(address_id)
);

CREATE TABLE bookstore.book_order (
    book_order_id  BIGINT PRIMARY KEY DEFAULT nextval('bookstore.book_order_id_seq'),
    customer_id    BIGINT        NOT NULL REFERENCES bookstore.customer(customer_id),
    order_total    NUMERIC(10,2) NOT NULL,
    order_status   VARCHAR(20)   NOT NULL
);

CREATE TABLE bookstore.order_item (
    order_item_id  BIGINT PRIMARY KEY DEFAULT nextval('bookstore.order_item_id_seq'),
    order_number   BIGINT        NOT NULL REFERENCES bookstore.book_order(book_order_id),
    book_id        BIGINT        NOT NULL REFERENCES bookstore.book(book_id),
    quantity       INTEGER       NOT NULL,
    price          NUMERIC(10,2) NOT NULL
);