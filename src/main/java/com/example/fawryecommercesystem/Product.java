package com.example.fawryecommercesystem;

import java.time.LocalDate;

public abstract class Product implements ShippableItem{
    protected String name;
    protected double price;
    protected int quantity;
    protected LocalDate expiryDate;
    protected boolean requiresShipping;
    protected double weight; // in kg

    public Product(String name, double price, int quantity, boolean requiresShipping, double weight) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.requiresShipping = requiresShipping;
        this.weight = weight;
    }

    // Getters and setters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public boolean requiresShipping() { return requiresShipping; }
    public double getWeight() { return weight; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public boolean isExpired() {
        return expiryDate != null && expiryDate.isBefore(LocalDate.now());
    }

    public boolean isInStock(int requestedQuantity) {
        return quantity >= requestedQuantity;
    }

    // ✅ Add these two:
    public boolean isShippable() {
        return requiresShipping;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }
}
