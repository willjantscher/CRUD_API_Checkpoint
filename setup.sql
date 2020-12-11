CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(50),
    password VARCHAR(50),
    PRIMARY KEY     (id)
);

INSERT INTO users (email, password) VALUES ('john@example.com', '123');
INSERT INTO users (email, password) VALUES ('eliza@example.com', 'password');
INSERT INTO users (email, password) VALUES ('jane@example.com', '1234');
INSERT INTO users (email, password) VALUES ('peter@example.com', 'nothing');