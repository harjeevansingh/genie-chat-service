CREATE DATABASE IF NOT EXISTS chat_db;
USE chat_db;

CREATE TABLE IF NOT EXISTS conversation (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT       NOT NULL,
    title      VARCHAR(255) NOT NULL,
    created_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    index idx_id_created_at (id, created_at),
    index idx_created_at (created_at),
    index idx_updated_at (updated_at),
    index idx_user_id (user_id)
);

CREATE TABLE IF NOT EXISTS task (
    id          bigint auto_increment
        primary key,
    created_at  datetime     not null,
    updated_at  datetime     not null,
    active      bit          not null,
    description varchar(255) null,
    due_date    datetime     null,
    status      varchar(255) null,
    title       varchar(255) null
);

CREATE TABLE IF NOT EXISTS users (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    email      VARCHAR(100) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    first_name VARCHAR(50),
    last_name  VARCHAR(50),
    is_active  BOOLEAN   DEFAULT TRUE,
    created_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    index idx_id_created_at (id, created_at),
    index idx_created_at (created_at),
    index idx_updated_at (updated_at),
    index idx_user_id (email)
);