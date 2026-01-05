package com.example.zencom.zencom_shop.modules.orders.application.ports.inventory;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public interface InventoryPort {

    //Validation method to ensure a product has stock in order to create a new Order
    boolean hasAvailable(ProductId productId, int quantity);

    //Reserve X quantity of a product when an Order is created
    void reserve(ProductId productId, int quantity);

    void commit(ProductId productId, int quantity);

    void release(ProductId productId, int quantity);

}
