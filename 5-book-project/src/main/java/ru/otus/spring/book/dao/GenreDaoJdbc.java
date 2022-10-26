package ru.otus.spring.book.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.book.domain.Book;
import ru.otus.spring.book.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GenreDaoJdbc implements GenreDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public GenreDaoJdbc(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int count() {
        Integer count = jdbcTemplate.queryForObject("select count(*) from genres", new HashMap<>(), Integer.class);
        return count == null? 0: count;
    }

    @Override
    public Long insert(Genre genre) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", genre.getTitle());
        SqlParameterSource paramSource = new MapSqlParameterSource(params);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("insert into genres (title) values (:title)",
                paramSource, keyHolder, new String[] { "id" });
        Long id = keyHolder.getKey().longValue();
        genre.setId(id);
        return id;
    }

    @Override
    public Genre getById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        List<Genre> genres = jdbcTemplate.query("select id, title from genres where id = :id",
                param, new GenreMapper());

        return genres.stream().findFirst().orElse(null);
    }

    @Override
    public List<Genre> getAll() {
        return jdbcTemplate.query("select id, title from genres", new GenreMapper());
    }

    @Override
    public void deleteById(long id) {
        SqlParameterSource param = new MapSqlParameterSource("id", id);
        jdbcTemplate.update("delete from genres where id = :id", param);
    }


    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            return new Genre(id, title);
        }
    }
}
