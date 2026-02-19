package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;

public interface GetProductByNameUseCase {
    ProductResultDTO getProductByName(String name);
}
