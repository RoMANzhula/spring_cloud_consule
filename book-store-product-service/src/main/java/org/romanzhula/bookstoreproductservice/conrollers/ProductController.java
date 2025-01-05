package org.romanzhula.bookstoreproductservice.conrollers;

import org.romanzhula.bookstoreproductservice.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/product-servie")
public class ProductController {

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return List.of(
                new Product(1L, "First Step", 299.99),
                new Product(2L, "Big Mouse", 159.50)
        );
    }

}
