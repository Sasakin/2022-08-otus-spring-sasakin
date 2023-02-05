alter table authors
    owner to postgres;

INSERT INTO public.authors (id, name) VALUES (1, 'masha');
INSERT INTO public.authors (id, name) VALUES (2, 'Nill Geyman');
INSERT INTO public.authors (id, name) VALUES (3, 'Stiven King');
INSERT INTO public.authors (id, name) VALUES (4, 'Maksim Sasakin');


alter table book_comments
    owner to postgres;

INSERT INTO public.book_comments (id, text, book_id) VALUES (1, 'Good book', null);
INSERT INTO public.book_comments (id, text, book_id) VALUES (2, 'Good book', null);


alter table genres
    owner to postgres;

INSERT INTO public.genres (id, title) VALUES (1, 'horor');
INSERT INTO public.genres (id, title) VALUES (2, 'Mystic');
INSERT INTO public.genres (id, title) VALUES (3, 'Trevel');


alter table books
    owner to postgres;

INSERT INTO public.books (id, title, author_id, genre_id) VALUES (3, 'Strong man', 4, 3);
