package com.example.fawryecommercesystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HelloController {

    @FXML private Label quantityCheese;
    @FXML private Label bos;
    @FXML private Label quantityTV;
    @FXML private Label quantityVouchers;
    @FXML private Label scratchersQuantity;

    @FXML private Label cheeseShipmentAmount;
    @FXML private Label biscuitsShipmentAmount;
    @FXML private Label tvShipmentAmount;
    @FXML private Label voucherShipmentAmount;
    @FXML private Label scratcherShipmentAmount;

    @FXML private Label cheeseWeight;
    @FXML private Label biscuitsWeight;
    @FXML private Label tvWeight;
    @FXML private Label voucherWeight;
    @FXML private Label scratcherWeight;

    @FXML private Label cheesePrice;
    @FXML private Label biscuitsPrice;
    @FXML private Label tvPrice;
    @FXML private Label voucherPrice;
    @FXML private Label scratcherPrice;

    @FXML private Label subtotal;
    @FXML private Label shipping;
    @FXML private Label amount;

    // Newly added product name display textareas
    @FXML private Label cheeseAmount;
    @FXML private Label biscuitsAmount;
    @FXML private Label TVAmount;
    @FXML private Label voucherAmount;
    @FXML private Label scratcherAmount;

    private Cart cart;
    private Customer customer;
    private Map<String, Product> products;
    private Map<String, Integer> quantities;

    public void initialize() {
        initializeProducts();
        initializeCustomer();
        cart = new Cart();

        quantities = new HashMap<>();
        for (String product : products.keySet()) {
            quantities.put(product, 0);
        }

        // Set product names in amount text areas
        cheeseAmount.setText("Cheese");
        biscuitsAmount.setText("Biscuits");
        TVAmount.setText("TV");
        voucherAmount.setText("Vouchers");
        scratcherAmount.setText("Scratchers");

        updateDisplay();
    }

    private void initializeProducts() {
        products = new HashMap<>();
        products.put("Cheese", new ExpirableProduct("Cheese", 100, 50, true, 0.2, LocalDate.now().plusDays(7)));
        products.put("Biscuits", new ExpirableProduct("Biscuits", 150, 30, true, 0.7, LocalDate.now().plusDays(30)));
        products.put("TV", new NonExpirableProduct("TV", 500, 10, true, 5.0));
        products.put("Vouchers", new NonExpirableProduct("Vouchers", 25, 100, false, 0.0));
        products.put("Scratchers", new NonExpirableProduct("Scratchers", 10, 200, false, 0.0));
    }

    private void initializeCustomer() {
        customer = new Customer("Usama Elareeny", 2000.0);
    }

    @FXML private void addCheese() { addToCart("Cheese", quantityCheese); }
    @FXML private void addBiscuits() { addToCart("Biscuits", bos); }
    @FXML private void addTV() { addToCart("TV", quantityTV); }
    @FXML private void addVouchers() { addToCart("Vouchers", quantityVouchers); }
    @FXML private void addScratchers() { addToCart("Scratchers", scratchersQuantity); }

    private void addToCart(String productName, Label quantityLabel) {
        try {
            int quantity = Integer.parseInt(quantityLabel.getText());
            if (quantity <= 0) {
                showAlert("Error", "Please set a valid quantity first!");
                return;
            }

            Product product = products.get(productName);
            cart.addItem(product, quantity);

            quantities.put(productName, 0);
            quantityLabel.setText("0");

            updateDisplay();
            showAlert("Success", quantity + "x " + productName + " added to cart!");
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid quantity format!");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    @FXML private void checkout() {
        try {
            CheckoutService.checkout(customer, cart);
            cart.clear();
            updateDisplay();
            showAlert("Success", "Checkout completed successfully!");
        } catch (Exception e) {
            showAlert("Error", e.getMessage());
        }
    }

    private void updateDisplay() {
        updateShipmentNotice();
        updateCheckoutReceipt();
        updateTotals();
    }

    private void updateShipmentNotice() {
        cheeseShipmentAmount.setText(""); biscuitsShipmentAmount.setText(""); tvShipmentAmount.setText("");
        voucherShipmentAmount.setText(""); scratcherShipmentAmount.setText("");

        cheeseWeight.setText(""); biscuitsWeight.setText(""); tvWeight.setText("");
        voucherWeight.setText(""); scratcherWeight.setText("");

        cheeseAmount.setText(""); biscuitsAmount.setText(""); TVAmount.setText("");
        voucherAmount.setText(""); scratcherAmount.setText("");

        for (CartItem item : cart.getItems()) {
            String name = item.getProduct().getName();
            int quantity = item.getQuantity();
            double weight = item.getWeight();
            String itemText = quantity + "x " + name;
            String weightText = String.format("%.1fkg", weight);

            switch (name) {
                case "Cheese" -> {
                    cheeseShipmentAmount.setText(itemText);
                    cheeseWeight.setText(weightText);
                    cheeseAmount.setText(itemText); // Sync with cheeseAmount
                }
                case "Biscuits" -> {
                    biscuitsShipmentAmount.setText(itemText);
                    biscuitsWeight.setText(weightText);
                    biscuitsAmount.setText(itemText); // Sync with biscuitsAmount
                }
                case "TV" -> {
                    tvShipmentAmount.setText(itemText);
                    tvWeight.setText(weightText);
                    TVAmount.setText(itemText); // Sync with TVAmount
                }
                case "Vouchers" -> {
                    voucherShipmentAmount.setText(itemText);
                    voucherWeight.setText(weightText);
                    voucherAmount.setText(itemText); // Sync with voucherAmount
                }
                case "Scratchers" -> {
                    scratcherShipmentAmount.setText(itemText);
                    scratcherWeight.setText(weightText);
                    scratcherAmount.setText(itemText); // Sync with scratcherAmount
                }
            }
        }
    }

    private void updateCheckoutReceipt() {
        cheesePrice.setText(""); biscuitsPrice.setText(""); tvPrice.setText("");
        voucherPrice.setText(""); scratcherPrice.setText("");

        for (CartItem item : cart.getItems()) {
            String name = item.getProduct().getName();
            double totalPrice = item.getTotalPrice();
            String priceText = "$" + totalPrice;

            switch (name) {
                case "Cheese" -> cheesePrice.setText(priceText);
                case "Biscuits" -> biscuitsPrice.setText(priceText);
                case "TV" -> tvPrice.setText(priceText);
                case "Vouchers" -> voucherPrice.setText(priceText);
                case "Scratchers" -> scratcherPrice.setText(priceText);
            }
        }
    }

    private void updateTotals() {
        double subtotalAmount = cart.getSubtotal();
        double shippingFee = ShippingService.calculateShippingFee(cart.getShippableItems());
        double totalAmount = subtotalAmount + shippingFee;

        subtotal.setText("$" + subtotalAmount);
        shipping.setText("$" + shippingFee);
        amount.setText("$" + totalAmount);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(title.equals("Error") ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void incrementCheese() { increment("Cheese", quantityCheese); }
    @FXML private void decrementCheese() { decrement("Cheese", quantityCheese); }

    @FXML private void incrementBiscuits() { increment("Biscuits", bos); }
    @FXML private void decrementBiscuits() { decrement("Biscuits", bos); }

    @FXML private void incrementTV() { increment("TV", quantityTV); }
    @FXML private void decrementTV() { decrement("TV", quantityTV); }

    @FXML private void incrementVouchers() { increment("Vouchers", quantityVouchers); }
    @FXML private void decrementVouchers() { decrement("Vouchers", quantityVouchers); }

    @FXML private void incrementScratchers() { increment("Scratchers", scratchersQuantity); }
    @FXML private void decrementScratchers() { decrement("Scratchers", scratchersQuantity); }

    private void increment(String name, Label label) {
        int current = quantities.getOrDefault(name, 0);
        Product product = products.get(name);
        if (current < product.getQuantity()) {
            current++;
            quantities.put(name, current);
            label.setText(String.valueOf(current));
        } else {
            showAlert("Error", "Maximum available quantity reached!");
        }
    }

    private void decrement(String name, Label label) {
        int current = quantities.getOrDefault(name, 0);
        if (current > 0) {
            current--;
            quantities.put(name, current);
            label.setText(String.valueOf(current));
        }
    }
}
