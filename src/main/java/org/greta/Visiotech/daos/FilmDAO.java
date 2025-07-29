package org.greta.Visiotech.daos;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmDAO {
    private final JdbcTemplate jdbcTemplate;

    public FilmDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<org.greta.Visiotech.entities.Film> productRowMapper = (rs, ignored) -> new org.greta.Visiotech.entities.Film(
            rs.getLong("filmId"),
            rs.getString("title"),
            rs.getString("synopsis"),
            rs.getString("date"),
            rs.getString("url")
    );

    public List<org.greta.Visiotech.entities.Film> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public org.greta.Visiotech.entities.Film findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.query(sql, productRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produit avec l'ID : " + id + " n'existe pas"));
    }

    public org.greta.Visiotech.entities.Film save(org.greta.Visiotech.entities.Film film) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        jdbcTemplate.update(sql, film.getTitle(), film.getSynopsis());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(sqlGetId, Long.class);

        film.setFilmId(id);
        return film;
    }

    public org.greta.Visiotech.entities.Film update(Long id, org.greta.Visiotech.entities.Film film) {
        if (!productExists(id)) {
            throw new RuntimeException("Produit avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, film.getTitle(), film.getSynopsis(), id);

        if (rowsAffected <= 0) {
            throw new RuntimeException("Échec de la mise à jour du produit avec l'ID : " + id);
        }

        return this.findById(id);
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

    private boolean productExists(Long id) {
        String checkSql = "SELECT COUNT(*) FROM products WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);
        return count > 0;
    }
}

