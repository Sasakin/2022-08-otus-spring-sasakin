ALTER TABLE books
DROP CONSTRAINT IF EXISTS fk_book_author;

ALTER TABLE books
DROP CONSTRAINT IF EXISTS fk_book_genre;

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID SERIAL PRIMARY KEY,
                   TITLE VARCHAR(255),
                   author_id BIGINT,
                   genre_id BIGINT);

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID SERIAL PRIMARY KEY,
                     NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID SERIAL PRIMARY KEY,
                    TITLE VARCHAR(255));

ALTER TABLE books
    ADD CONSTRAINT fk_book_author
        foreign key (author_id)
            REFERENCES authors (id);

ALTER TABLE books
    ADD CONSTRAINT fk_book_genre
        foreign key (genre_id)
            REFERENCES genres (id);
