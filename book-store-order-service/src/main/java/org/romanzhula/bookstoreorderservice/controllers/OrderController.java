package org.romanzhula.bookstoreorderservice.controllers;

import org.romanzhula.bookstoreorderservice.models.OrderInfo;
import org.romanzhula.bookstoreorderservice.models.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/order-service")
public class OrderController implements RowMapper<OrderInfo> {

    private final RestTemplate restTemplate;
    private final JdbcTemplate jdbcTemplate;

    public OrderController(
            RestTemplate restTemplate,
            JdbcTemplate jdbcTemplate
    ) {
        this.restTemplate = restTemplate;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/orders")
    public List<OrderInfo> getAllOrders() {
        return this.jdbcTemplate.query("SELECT * FROM order_info", this);
    }


    @Override
    public OrderInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        var productId = rs.getLong("product_id");

        Product product =
                this.restTemplate.getForObject(
                        "http://product-service/api/product-service/products/" + productId,
                        Product.class
                )
        ;

        return new OrderInfo(
                rs.getLong("id"),
                rs.getInt("amount"),
                product,
                rs.getDouble("total_cost")
        );
    }

}
