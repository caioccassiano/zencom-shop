package com.example.zencom.zencom_shop.modules.checkout.application.ports;

import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.catalog.ProductsSnapshots;

import java.util.List;
import java.util.UUID;

public interface ProductsPort {
    ProductsSnapshots getProducts(List<UUID> productIds);
}
