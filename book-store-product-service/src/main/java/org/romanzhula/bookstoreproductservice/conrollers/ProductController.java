package org.romanzhula.bookstoreproductservice.conrollers;

import org.romanzhula.bookstoreproductservice.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-service")
public class ProductController {

    private final JdbcTemplate jdbcTemplate;

    public ProductController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return this.jdbcTemplate.query("SELECT * FROM products", (rs, rowNum) ->
                new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                )
        );
    }

    @GetMapping("/products/{id}")
    public Product getProducts(@PathVariable int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return this.jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getDouble("price")
        ), id);
    }

}
