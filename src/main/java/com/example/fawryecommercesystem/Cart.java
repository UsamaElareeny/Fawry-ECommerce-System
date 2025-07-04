package com.example.fawryecommercesystem;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) throws Exception {
        if (product.isExpired()) {
            throw new Exception("Product " + product.getName() + " is expired!");
        }

        if (!product.isInStock(quantity)) {
            throw new Exception("Insufficient stock for " + product.getName() +
                    ". Available: " + product.getQuantity() + ", Requested: " + quantity);
        }

        // Check if product already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().getName().equals(product.getName())) {
                int newQuantity = item.getQuantity() + quantity;
                if (!product.isInStock(newQuantity)) {
                    throw new Exception("Insufficient stock for " + product.getName() +
                            ". Available: " + product.getQuantity() + ", Requested: " + newQuantity);
                }
                item.setQuantity(newQuantity);
                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }


    public void clear() {
        items.clear();
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getSubtotal() {
        return items.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public List<ShippableItem> getShippableItems() {
        return items.stream()
                .filter(item -> item.getProduct().requiresShipping())
                .map(item -> (ShippableItem) item)
                .toList();
    }
}