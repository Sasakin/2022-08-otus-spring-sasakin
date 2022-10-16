package ru.otus.spring.book.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoJdbc implements BookDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private AuthorDao authorDao;

    private GenreDao genreDao;

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate, AuthorDao authorDao, GenreDao genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public int count() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from books", new HashMap<>(), Integer.class);
        return count == null? 0: count;
    }

    @Override
    public void insert(Book book) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", book.getId());
        paramMap.put("title", book.getTitle());
        paramMap.put("author_id", book.getAuthor() == null ? "" : book.getAuthor().getId());
        paramMap.put("genre_id", book.getGenre() == null ? "" : book.getGenre().getId());

        jdbcTemplate.update("insert into books (id, title, author_id, genre_id) " +
                            "values (:id, :title, :author_id, :genre_id)",
                            paramMap);
    }

    @Override
    public Book getById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        Book book = jdbcTemplate.queryForObject("select id, title, author_id, genre_id from books where id = :id",
                param, new BookMapper(authorDao, genreDao));
        return book;
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("select id, title, author_id, genre_id from books", new BookMapper(authorDao, genreDao));
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update("delete from books where id = :id", param);
    }

    private static class BookMapper implements RowMapper<Book> {

        private AuthorDao authorDao;

        private GenreDao genreDao;

        public BookMapper(AuthorDao authorDao, GenreDao genreDao) {
            this.authorDao = authorDao;
            this.genreDao = genreDao;
        }

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            Author author = authorDao.getById(resultSet.getLong("author_id"));
            Genre genre = genreDao.getById(resultSet.getLong("genre_id"));
            return new Book(id, title, author, genre);
        }
    }
}
