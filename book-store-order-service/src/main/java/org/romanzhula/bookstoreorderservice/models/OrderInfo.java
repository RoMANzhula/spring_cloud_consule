package org.romanzhula.bookstoreorderservice.models;

public record OrderInfo(Long id, int amount, Product product, double totalCost) {
}
