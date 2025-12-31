package com.example.zencom.zencom_shop.modules.inventory.domain.entities;

import com.example.zencom.zencom_shop.modules.inventory.domain.exceptions.InsufficientReservedStockException;
import com.example.zencom.zencom_shop.modules.inventory.domain.exceptions.InvalidStockQuantityException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.time.Instant;

public class InventoryItem {
    private final ProductId productId;
    private int availableQuantity;
    private int reservedQuantity;
    private Instant createdAt;
    private Instant updatedAt;

    private InventoryItem(ProductId productId,
                          int availableQuantity,
                          int reservedQuantity,
                          Instant createdAt,
                          Instant updatedAt) {

        if(productId == null) throw new IllegalArgumentException("productId cannot be null");
        if(availableQuantity < 0) throw new IllegalArgumentException("availableQuantity cannot be negative");
        if(reservedQuantity < 0) throw new IllegalArgumentException("reservedQuantity cannot be negative");

        this.productId = productId;
        this.availableQuantity = availableQuantity;
        this.reservedQuantity = reservedQuantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static InventoryItem create(
            ProductId productId){
        Instant now = Instant.now();
        return new InventoryItem(productId, 0, 0, now, now);
    }

    //Add Stock (ADMIN only)
    public void addStock(int quantity){
        if(quantity <= 0) throw new InvalidStockQuantityException();
        this.availableQuantity += quantity;
        touch();
    }

    //Reserve: move from available -> reserved
    // It avoids oversell (available gets subtract as soon as a reservation is made)
    public void reserveStock(int quantity){
        if(quantity < 0) throw new InvalidStockQuantityException();
        this.reservedQuantity -= quantity;
        this.reservedQuantity += quantity;
        touch();
    }

    // Release stock from Reserved -> Available (e.g: when an order is not approved)
    public void releaseStock(int quantity){
        if(quantity < 0) throw new InvalidStockQuantityException();
        if(this.reservedQuantity < quantity) throw new InsufficientReservedStockException();
        this.availableQuantity -= quantity;
        this.availableQuantity += quantity;
        touch();
    }

    //Confirm product has been sold. Subtracts from reservedStock
    public void commit(int quantity){
        if(quantity <= 0) throw new InvalidStockQuantityException();
        if(this.reservedQuantity < quantity) throw new InsufficientReservedStockException();
        this.reservedQuantity -= quantity;
        touch();
    }


    public void touch(){
        this.updatedAt = Instant.now();
    }

    public ProductId getProductId() {
        return productId;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public int getReservedQuantity() {
        return reservedQuantity;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public int totalQuantity(){
        return availableQuantity + reservedQuantity;
    }






}
