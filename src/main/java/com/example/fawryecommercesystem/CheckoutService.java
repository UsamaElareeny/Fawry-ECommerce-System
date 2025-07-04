package com.example.fawryecommercesystem;

import java.util.List;

public class CheckoutService {

    public static void checkout(Customer customer, Cart cart) throws Exception {
        if (cart.isEmpty()) {
            throw new Exception("Cart is empty!");
        }

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.isExpired()) {
                throw new Exception("Product " + product.getName() + " is expired!");
            }
            if (!product.isInStock(item.getQuantity())) {
                throw new Exception("Product " + product.getName() + " is out of stock!");
            }
        }

        double subtotal = cart.getSubtotal();
        List<ShippableItem> shippableItems = cart.getShippableItems();
        double shippingFee = ShippingService.calculateShippingFee(shippableItems);
        double totalAmount = subtotal + shippingFee;

        if (customer.getBalance() < totalAmount) {
            throw new Exception("Insufficient balance! Required: $" + totalAmount +
                    ", Available: $" + customer.getBalance());
        }

        ShippingService.processShipment(shippableItems);

        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.setQuantity(product.getQuantity() - item.getQuantity());
        }

        customer.deductBalance(totalAmount);
    }
}