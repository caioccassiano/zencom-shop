package com.example.zencom.zencom_shop.modules.cart.domain.entities;

import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;
import com.example.zencom.zencom_shop.modules.cart.domain.exceptions.CartIsEmptyException;
import com.example.zencom.zencom_shop.modules.cart.domain.exceptions.CartItemNotFoundException;
import com.example.zencom.zencom_shop.modules.cart.domain.exceptions.InvalidCartItemException;
import com.example.zencom.zencom_shop.modules.cart.domain.vo.CartId;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Cart {
    CartId cartId;
    UUID userId;
    CartStatus status;
    List<CartItem> cartItemList;
    Instant createdAt;
    Instant updatedAt;

    private Cart(CartId cartId,
                 UUID userId,
                 CartStatus status,
                 List<CartItem> items,
                 Instant createdAt,
                 Instant updatedAt
                 ) {
        this.cartId = Objects.requireNonNull(cartId, "CartId is null");
        this.userId = Objects.requireNonNull(userId, "UserId is null");
        this.status = Objects.requireNonNull(status, "CartStatus is null");
        this.cartItemList = new ArrayList<>(Objects.requireNonNull(items, "CartItemList is null"));
        this.createdAt = Objects.requireNonNull(createdAt, "CreatedAt is null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "UpdatedAt is null");
    }

    public static Cart create(UUID userId){
        Instant now = Instant.now();
        return new Cart(
                CartId.newId(),
                userId,
                CartStatus.ACTIVE,
                List.of(),
                now,
                now
        );
    }

    public static Cart restore(
            CartId id,
            UUID userId,
            CartStatus status,
            List<CartItem> items,
            Instant createdAt,
            Instant updatedAt
    ){
        return new Cart(
                id,
                userId,
                status,
                items,
                createdAt,
                updatedAt
        );
    }

    public void addNewItem(ProductId productId,
                           int quantity,
                           BigDecimal unitPrice) {
        ensureIsActive();
        CartItem existingItem = FindItemOrNull(productId);
        if (existingItem != null) {
            existingItem.increase(quantity);
            touch();
            return;
        }
        this.cartItemList.add(CartItem.create(productId, quantity, unitPrice));
        touch();
    }

    public void updateQuantity(ProductId productId, int newQuantity) {
        ensureIsActive();
        CartItem existingItem = FindItemOrNull(productId);
        if(existingItem == null){
            throw new CartItemNotFoundException("Product not found");
        }
        if(newQuantity == 0){
            this.cartItemList.remove(existingItem);
            touch();
            return;
        }
        existingItem.changeQuantity(newQuantity);
        touch();
    }

    public void removeItem(ProductId productId) {
        ensureIsActive();
        CartItem existingItem = FindItemOrNull(productId);
        if(existingItem == null){
            throw new CartItemNotFoundException("Product not found");
        }
        this.cartItemList.remove(existingItem);
        touch();
    }

    public void clearCart(){
        ensureIsActive();
        this.cartItemList.clear();
        touch();
    }

    public void checkout(){
        ensureIsActive();
        ensureIsNotEmpty();
        this.status = CartStatus.CHECKED_OUT;
        touch();
    }

    public void abandon(){
        ensureIsActive();
        this.status = CartStatus.ABANDONED;
        touch();
    }

    public boolean isEmpty(){
        return this.cartItemList.isEmpty();
    }

    public BigDecimal subtotal(){
        BigDecimal total = BigDecimal.ZERO;
        for(CartItem item :cartItemList){
            total = total.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return total;
    }

    private void ensureIsNotEmpty(){
        if(this.cartItemList.isEmpty()){
            throw new CartIsEmptyException("CartItemList is empty");
        }
    }

    public void ensureIsActive(){
        if(this.status != CartStatus.ACTIVE){
            throw new InvalidCartItemException("CartStatus is not ACTIVE");
        }
    }

    public CartItem FindItemOrNull(ProductId productId) {
        Objects.requireNonNull(productId, "ProductId is null");
        for (CartItem item : cartItemList) {
            if (item.getProductId().equals(productId)) {
                return item;
            }
        }
        return null;
    }

    public void touch(){
        this.updatedAt = Instant.now();
    }

    public CartId getCartId() {
        return cartId;
    }

    public UUID getUserId() {
        return userId;
    }

    public CartStatus getStatus() {
        return status;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
