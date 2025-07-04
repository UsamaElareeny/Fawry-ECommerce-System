package com.example.fawryecommercesystem;

public class NonExpirableProduct extends Product {
    public NonExpirableProduct(String name, double price, int quantity, boolean requiresShipping, double weight) {
        super(name, price, quantity, requiresShipping, weight);
    }
}