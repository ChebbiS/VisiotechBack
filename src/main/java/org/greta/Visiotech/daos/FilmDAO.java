package org.greta.Visiotech.daos;

import org.greta.Visiotech.exceptions.ResourceNotFoundException;
import org.greta.Visiotech.exceptions.TechnicalDatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.greta.Visiotech.entities.Film;

import java.util.List;

@Repository
public class FilmDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public FilmDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Film> productRowMapper = (rs, rowNum) -> new Film(
            rs.getLong("id"),
            rs.getString("title"),
            rs.getString("description"),
            rs.getInt("release_year"),
            null,
            null,
            rs.getString("poster_url")
    );

    public List<Film> findAll() {
        try {
            String sql = "SELECT * FROM movies";
            return jdbcTemplate.query(sql, productRowMapper);
        } catch (DataAccessException e) {
            throw new TechnicalDatabaseException("Erreur lors de la récupération des films", e);
        }
    }

    public Film findById(Long id) {
        try {
            String sql = "SELECT * FROM movies WHERE id = ?";
            return jdbcTemplate.query(sql, productRowMapper, id)
                    .stream()
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Film avec l'ID : " + id + " n'existe pas"));
        } catch (DataAccessException e) {
            throw new TechnicalDatabaseException("Erreur lors de la recherche de film avec l'ID : " + id, e);
        }
    }

    public Film save(Film film) {
        try {
        String sql = "INSERT INTO movies (title, description, release_year, poster_url) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getPosterUrl());

        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        film.setId(id);
        return film;
    } catch (DataAccessException e) {
            throw new TechnicalDatabaseException("Erreur lors de la sauvegarde du film : " + film.getTitle(), e);
        }
    }
    public Film update(Long id, Film film) {
        try {
        if (!filmExists(id)) {
            throw new ResourceNotFoundException("Film avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE movies SET title = ?, description = ?, release_year = ?, poster_url = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql,
                film.getTitle(),
                film.getDescription(),
                film.getReleaseYear(),
                film.getPosterUrl(),
                id);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour du Film avec l'ID : " + id);
        }

        return this.findById(id);
    } catch (DataAccessException e) {
            throw new TechnicalDatabaseException("Erreur lors de la mise à jour du film avec l'ID : " + id, e);
        }
        }

    public boolean delete(Long id) {
        try {
        String sql = "DELETE FROM movies WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    } catch (DataAccessException e) {
            throw new TechnicalDatabaseException("Erreur lors de la suppression du film avec l'ID : " + id, e);
        }
    }

    private boolean filmExists(Long id) {
        try {
            String checkSql = "SELECT COUNT(*) FROM movies WHERE id = ?";
            Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
            return count != null && count > 0;
        } catch (DataAccessException e) {
            throw new TechnicalDatabaseException("Erreur lors de la vérification de l'existence du film avec l'ID : " + id, e);
        }
    }
}
