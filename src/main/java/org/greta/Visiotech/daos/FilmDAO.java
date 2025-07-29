package org.greta.Visiotech.daos;

import org.greta.Visiotech.entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class actor {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, ignored) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getDouble("price")
    );

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.query(sql, productRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produit avec l'ID : " + id + " n'existe pas"));
    }

    public Product save(Product product) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice());

        String sqlGetId = "SELECT LAST_INSERT_ID()";
        Long id = jdbcTemplate.queryForObject(sqlGetId, Long.class);

        product.setId(id);
        return product;
    }

    public Product update(Long id, Product product) {
        if (!productExists(id)) {
            throw new RuntimeException("Produit avec l'ID : " + id + " n'existe pas");
        }

        String sql = "UPDATE products SET name = ?, price = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, product.getName(), product.getPrice(), id);

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

