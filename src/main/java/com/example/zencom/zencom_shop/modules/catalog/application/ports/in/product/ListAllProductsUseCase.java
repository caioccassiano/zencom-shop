package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;

import java.util.List;

public interface ListAllProductsUseCase {
    List<ProductResultDTO> getAll();
}
