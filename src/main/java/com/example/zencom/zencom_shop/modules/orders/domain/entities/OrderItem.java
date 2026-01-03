package com.example.zencom.zencom_shop.modules.orders.domain.entities;

import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ItemQuantityInvalidException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ItemUnitPriceInvalidException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ProductIdICannotBeNullException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ProductNameInvalidException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;

public class OrderItem {
    private final ProductId productId;
    private final String productName;
    private final BigDecimal unitPrice;
    private final int quantity;


    private OrderItem(ProductId productId, String productName, BigDecimal unitPrice, int quantity) {
        if (productId == null) throw new ProductIdICannotBeNullException();
        if (productName == null || productName.isBlank()) throw new ProductNameInvalidException();
        if (unitPrice == null || unitPrice.signum() <= 0) throw new ItemUnitPriceInvalidException();
        if (quantity <= 0) throw new ItemQuantityInvalidException();

        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public static OrderItem create(
            ProductId productId, String productName, BigDecimal unitPrice, int quantity){
        return new OrderItem(productId, productName, unitPrice, quantity);
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
    public ProductId getProductId() {
        return productId;
    }
    public String getProductName() {
        return productName;
    }
    public BigDecimal getUnitPrice() {
        return unitPrice;
    }
    public int getQuantity() {
        return quantity;
    }


}
