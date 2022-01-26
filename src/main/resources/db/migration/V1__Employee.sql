CREATE SEQUENCE IF NOT EXISTS confirmation_token_sequence START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS employee_category_sequence START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS employee_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE confirmation_token
(
    id           BIGINT       NOT NULL,
    token        VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expires_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    confirmed_at TIMESTAMP WITHOUT TIME ZONE,
    employee_id  BIGINT       NOT NULL,
    CONSTRAINT pk_confirmationtoken PRIMARY KEY (id)
);

CREATE TABLE employee
(
    id            BIGINT NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(255),
    employee_role VARCHAR(255),
    locked        BOOLEAN,
    enabled       BOOLEAN,
    CONSTRAINT pk_employee PRIMARY KEY (id)
);

CREATE TABLE employee_category
(
    id          BIGINT       NOT NULL,
    name        VARCHAR(255) NOT NULL,
    employee_id BIGINT       NOT NULL,
    CONSTRAINT pk_employee_category PRIMARY KEY (id)
);

ALTER TABLE confirmation_token
    ADD CONSTRAINT FK_CONFIRMATIONTOKEN_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);

ALTER TABLE employee_category
    ADD CONSTRAINT FK_EMPLOYEE_CATEGORY_ON_EMPLOYEE FOREIGN KEY (employee_id) REFERENCES employee (id);