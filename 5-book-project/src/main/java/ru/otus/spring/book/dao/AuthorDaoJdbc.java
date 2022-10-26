package ru.otus.spring.book.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.domain.Author;
import ru.otus.spring.book.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AuthorDaoJdbc implements AuthorDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public AuthorDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int count() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from authors", new HashMap<>(), Integer.class);
        return count == null? 0: count;
    }

    @Override
    public Long insert(Author author) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", author.getName());
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into authors (name) values (:name)",
                paramSource, keyHolder, new String[] { "id" });
        Long id = keyHolder.getKey().longValue();
        author.setId(id);
        return id;
    }

    @Override
    public Author getById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        List<Author> authors = jdbcTemplate.query("select id, name from authors where id = :id",
                param, new AuthorMapper());
        return authors.stream().findFirst().orElse(null);
    }

    @Override
    public List<Author> getAll() {
        return jdbcTemplate.query("select id, name from authors", new AuthorMapper());
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update("delete from authors where id = :id", param);
    }


    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new Author(id, name);
        }
    }
}
