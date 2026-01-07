package com.example.zencom.zencom_shop.modules.cart.domain.entities;

import com.example.zencom.zencom_shop.modules.cart.domain.exceptions.InvalidCartItemException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class CartItem {
    ProductId productId;
    int quantity;
    BigDecimal unitPrice;
    Instant addedAt;


    public CartItem(ProductId productId, int quantity, BigDecimal unitPrice, Instant addedAt) {
        this.productId = Objects.requireNonNull(productId, "ProductId must not be null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "UnitPrice must not be null");
        this.addedAt = Objects.requireNonNull(addedAt, "AddedAt must not be null");

        if(unitPrice.signum() < 0) {
            throw new InvalidCartItemException("UnitPrice must not be negative");
        }

        if  (quantity <= 0) {
            throw new InvalidCartItemException("Quantity must be greater than zero");
        }
        this.quantity = quantity;
    }

    public static CartItem create(
            ProductId productId, int quantity, BigDecimal unitPrice){
        return new CartItem(productId, quantity, unitPrice, Instant.now());
    }

    public void increase(int quantity){
        if (quantity <= 0) {
            throw new InvalidCartItemException("Quantity must be greater than zero");
        }
        if(this.quantity > Integer.MAX_VALUE - quantity) {
            throw new InvalidCartItemException("Quantity must be less than or equal to Integer.MAX_VALUE");
        }

        this.quantity += quantity;
    }

    public void changeQuantity(int newQuantity){
        if (newQuantity <= 0) {
            throw new InvalidCartItemException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;

    }
    public BigDecimal total(){
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Instant getAddedAt() {
        return addedAt;
    }
}
