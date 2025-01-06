CREATE TABLE IF NOT EXISTS order_info (
                                          id BIGINT PRIMARY KEY,
                                          amount INT NOT NULL,
                                          product_id BIGINT NOT NULL,
                                          total_cost DOUBLE PRECISION NOT NULL
);
