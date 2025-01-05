package org.romanzhula.bookstoreorderservice.controllers;

import org.romanzhula.bookstoreorderservice.models.OrderInfo;
import org.romanzhula.bookstoreorderservice.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/order-service")
public class OrderController {

    private final RestTemplate restTemplate;

    public OrderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/orders")
    public List<OrderInfo> getAllOrders() {
        Product[] products =
                this.restTemplate.getForObject(
                        "http://product-service/api/product-servie/products",
                        Product[].class
                )
        ;

        if (products == null) return Collections.emptyList();

        return List.of(
                new OrderInfo(1L, 2, products[0], (2 * products[0].price())),
                new OrderInfo(2L, 1, products[1], products[1].price())
        );
    }

}
