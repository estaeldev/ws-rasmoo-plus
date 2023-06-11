CREATE TABLE if not exists users_credentials (
    users_credentials_id UUID NOT NULL PRIMARY KEY,
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL UNIQUE,
    user_type_id INTEGER,
    FOREIGN KEY (user_type_id) REFERENCES user_type(user_type_id)
);
