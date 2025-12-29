package com.example.zencom.zencom_shop.modules.catalog.application.mappers;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;

public final class ProductResultMapper {
    private ProductResultMapper() {}

    public static ProductResultDTO toResult(Product product){
        return new ProductResultDTO(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getStatus(),
        product.getCreatedAt(),
        product.getUpdatedAt()
        );
    }


}
