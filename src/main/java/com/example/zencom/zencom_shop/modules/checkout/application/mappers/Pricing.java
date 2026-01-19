package com.example.zencom.zencom_shop.modules.checkout.application.mappers;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart.CartItemSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart.CartSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.catalog.ProductSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.catalog.ProductsSnapshots;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders.CreateOrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Pricing {
    private final List<PricingItem> items;
    private final BigDecimal totalAmount;

    private Pricing(List<PricingItem> items, BigDecimal totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }
    public static Pricing from(CartSnapshot cart, ProductsSnapshots products) {
        List<PricingItem> pricingItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for(CartItemSnapshot cartItem : cart.items()){
            ProductSnapshot product = products.getProducts(cartItem.productId());
            if(product == null){
                throw new IllegalArgumentException("Product not found");
            }
            if(!product.active()){
                throw new IllegalArgumentException("Product not active");
            }
            BigDecimal lineTotal = product.price().multiply(BigDecimal.valueOf(cartItem.quantity()));
            pricingItems.add(
                    new PricingItem(
                            cartItem.productId(),
                            cartItem.quantity(),
                            product.price(),
                            lineTotal
                    )
            );
            total = total.add(lineTotal);
        }
        return new Pricing(pricingItems, total);
    }

    public BigDecimal totalAmount() {
        return totalAmount;
    }

    public List<CreateOrderItem> toOrderItems() {
        return items.stream()
                .map(i -> new CreateOrderItem(
                        i.productId(),
                        i.quantity(),
                        i.unitPrice()
                ))
                .toList();
    }
}
