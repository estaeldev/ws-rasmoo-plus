-- Active: 1680452414775@@127.0.0.1@5432@rasplus
CREATE TABLE if not exists subscriptions_type (
    subscriptions_type_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    access_months INTEGER ,
    price DECIMAL(10,2) NOT NULL,
    product_key VARCHAR UNIQUE
);

CREATE TABLE if not exists user_type (
    user_type_id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL,
    description VARCHAR NOT NULL
);

CREATE TABLE if not exists users (
    users_id UUID NOT NULL PRIMARY KEY,
    name VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    phone VARCHAR NOT NULL UNIQUE,
    cpf VARCHAR NOT NULL UNIQUE,
    dt_subscription DATE NOT NULL,
    dt_expiration DATE NOT NULL,
    user_type_id INTEGER,
    subscriptions_type_id INTEGER,
    FOREIGN KEY (user_type_id) REFERENCES user_type(user_type_id),
    FOREIGN KEY (subscriptions_type_id) REFERENCES subscriptions_type(subscriptions_type_id)
);

CREATE TABLE if not exists user_payment_info(
    user_payment_info_id SERIAL PRIMARY KEY,
    card_number VARCHAR NOT NULL UNIQUE,
    card_expiration_month INTEGER NOT NULL,
    card_expiration_year INTEGER NOT NULL,
    card_security_code VARCHAR NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    instalments INTEGER NOT NULL,
    dt_payment DATE NOT NULL,
    user_id UUID,
    FOREIGN KEY(user_id) REFERENCES users(users_id)
);

INSERT INTO subscriptions_type (name,access_months,price,product_key) VALUES ('PLANO MENSAL',1,75.00,'MONTH22');
INSERT INTO subscriptions_type (name,access_months,price,product_key) VALUES ('PLANO ANUAL',12,697.00,'YEAR22');
INSERT INTO subscriptions_type (name,access_months,price,product_key) VALUES ('PLANO VITALICIO',NULL,1497.00,'PERPETUAL22');

INSERT INTO user_type (name,description) VALUES ('PROFESSOR','Professores da plataforma - cadastro administrativo');
INSERT INTO user_type (name,description) VALUES ('ADMINISTRADOR','Administrado da plataforma - cadastro administrativo');
INSERT INTO user_type (name,description) VALUES ('ALUNO','Aluno da plataforma - cadastro via fluxo normal');