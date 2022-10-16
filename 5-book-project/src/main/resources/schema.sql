/*ALTER TABLE BOOKS
    DROP CONSTRAINT IF EXISTS fk_book_author;
ALTER TABLE BOOKS
DROP CONSTRAINT IF EXISTS fk_book_genre;*/

DROP TABLE IF EXISTS BOOKS;
CREATE TABLE BOOKS(ID BIGINT PRIMARY KEY,
                   TITLE VARCHAR(255),
                   author_id BIGINT,
                   genre_id BIGINT);

DROP TABLE IF EXISTS AUTHORS;
CREATE TABLE AUTHORS(ID BIGINT PRIMARY KEY,
                     NAME VARCHAR(255));

DROP TABLE IF EXISTS GENRES;
CREATE TABLE GENRES(ID BIGINT PRIMARY KEY,
                    TITLE VARCHAR(255));

/*ALTER TABLE BOOKS
    ADD CONSTRAINT fk_book_author
        foreign key (author_id)
        REFERENCES AUTHORS (id);

ALTER TABLE BOOKS
    ADD CONSTRAINT fk_book_genre
        foreign key (genre_id)
            REFERENCES GENRES (id);*/
