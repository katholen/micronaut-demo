CREATE TABLE users
(
    user_name VARCHAR(20) PRIMARY KEY UNIQUE NOT NULL,
    password  VARCHAR(20)                    NOT NULL
);
