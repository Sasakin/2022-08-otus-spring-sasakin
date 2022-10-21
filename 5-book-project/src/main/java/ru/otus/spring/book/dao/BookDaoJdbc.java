package ru.otus.spring.book.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public BookDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int count() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from books", new HashMap<>(), Integer.class);
        return count == null? 0: count;
    }

    @Override
    public Long insert(Book book) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("title", book.getTitle());
        paramMap.put("author_id", book.getAuthor() == null ? "" : book.getAuthor().getId());
        paramMap.put("genre_id", book.getGenre() == null ? "" : book.getGenre().getId());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
        jdbcTemplate.update("insert into books (title, author_id, genre_id) " +
                            "values (:title, :author_id, :genre_id)",
                            paramSource, keyHolder, new String[] { "id" });
        Long id = keyHolder.getKey().longValue();
        book.setId(id);
        return id;
    }

    @Override
    public Book getById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);

        List<Book> books = jdbcTemplate.query("select b.id, b.title, b.author_id, b.genre_id, \n" +
                        "                a.name, g.title \n" +
                        "                from books as b, authors as a, genres as g \n" +
                        "                where b.id = :id   " +
                        "                and b.author_id = a.id \n" +
                        "                and b.genre_id = g.id ",
                param, new BookMapper());

        return books.stream().findFirst().orElse(null);
    }

    @Override
    public List<Book> getAll() {
        return jdbcTemplate.query("select b.id, b.title, b.author_id, b.genre_id,\n" +
                "                a.name, g.title\n" +
                "                from books as b join authors as a\n" +
                "                  on b.author_id = a.id\n" +
                "                join genres as g\n" +
                "                  on b.genre_id = g.id;",
                new BookMapper());
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update("delete from books where id = :id", param);
    }

    private static class BookMapper implements RowMapper<Book> {

        private static final int COL_ID = 1;
        private static final int COL_TITLE = 2;
        private static final int COL_AUTHOR_ID = 3;
        private static final int COL_GENRE_ID = 4;
        private static final int COL_NAME = 5;
        private static final int COL_GENRE_TITLE = 6;

        public BookMapper() {
        }

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong(COL_ID);
            String title = resultSet.getString(COL_TITLE);
            Long authorId = resultSet.getLong(COL_AUTHOR_ID);
            Long genreId = resultSet.getLong(COL_GENRE_ID);
            String name = resultSet.getString(COL_NAME);
            String genreTitle = resultSet.getString(COL_GENRE_TITLE);
            Author author = new Author(authorId, name);
            Genre genre = new Genre(genreId, genreTitle);
            return new Book(id, title, author, genre);
        }
    }
}
