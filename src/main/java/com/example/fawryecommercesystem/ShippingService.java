package com.example.fawryecommercesystem;
import java.util.List;


public class ShippingService {
    private static final double SHIPPING_RATE_PER_KG = 15.0;

    public static void processShipment(List<ShippableItem> items) {
    }

    public static double calculateShippingFee(List<ShippableItem> items) {
        double totalWeight = items.stream().mapToDouble(ShippableItem::getWeight).sum();
        return totalWeight * SHIPPING_RATE_PER_KG;
    }
}