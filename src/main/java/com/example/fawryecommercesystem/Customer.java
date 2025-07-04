package com.example.fawryecommercesystem;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public double getBalance() { return balance; }

    public void deductBalance(double amount) throws Exception {
        if (balance < amount) {
            throw new Exception("Insufficient balance! Required: $" + amount + ", Available: $" + balance);
        }
        balance -= amount;
    }
}