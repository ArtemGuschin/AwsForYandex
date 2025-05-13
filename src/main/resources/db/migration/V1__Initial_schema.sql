CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(64)   NOT NULL UNIQUE,
    password   VARCHAR(2048) NOT NULL,
    role       VARCHAR(32)   NOT NULL,
    status     VARCHAR(32),
    first_name VARCHAR(64)   NOT NULL,
    last_name  VARCHAR(64)   NOT NULL,
    enabled    BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);


create table files
(
    id       SERIAL PRIMARY KEY,
    location varchar(255) not null,
    status   varchar(32)  not null

);

create table events
(
    id      SERIAL PRIMARY KEY,
    user_id int         not null references users (id) ON DELETE CASCADE,
    file_id int         not null references files (id) ON DELETE CASCADE,
    status  varchar(32) not null

);