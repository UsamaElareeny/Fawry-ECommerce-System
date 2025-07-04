package com.example.fawryecommercesystem;
import java.time.LocalDate;

public class ExpirableProduct extends Product {
    public ExpirableProduct(String name, double price, int quantity, boolean requiresShipping, double weight, LocalDate expiryDate) {
        super(name, price, quantity, requiresShipping, weight);
        this.expiryDate = expiryDate;
    }
}