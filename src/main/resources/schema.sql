DROP SCHEMA IF EXISTS filmsDb;
CREATE SCHEMA filmsDb;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS films_categories;

CREATE TABLE films_categories (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description VARCHAR(255),
    genre_name VARCHAR(255));

CREATE TABLE films (
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    price NUMERIC(32,2) NOT NULL,
    title VARCHAR(255),
    category_id INT NOT NULL,
    FOREIGN KEY (category_id) REFERENCES films_categories(id));
