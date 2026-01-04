package com.example.zencom.zencom_shop.modules.orders.application.ports.catalog;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductCatalogPort {

    Optional<ProductSnapshot> findActiveById(ProductId productId);


    record ProductSnapshot(
            ProductId productId,
            String name,
            BigDecimal price
    ){}
}
