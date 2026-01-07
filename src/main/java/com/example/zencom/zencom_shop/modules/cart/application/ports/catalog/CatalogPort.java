package com.example.zencom.zencom_shop.modules.cart.application.ports.catalog;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
import java.util.Optional;

public interface CatalogPort {
    Optional<ProductSnapshot> findActiveProduct(ProductId productId);


    record ProductSnapshot(
            ProductId productId,
            BigDecimal unitPrice
    ){}
}
