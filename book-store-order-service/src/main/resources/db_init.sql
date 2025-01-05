CREATE TABLE order_info(
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           amount INT NOT NULL,
                           product_id BIGINT NOT NULL,
                           total_cost DOUBLE NOT NULL,
                           FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DOUBLE NOT NULL
);