package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.mappers;

import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.response.ProductResponse;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.response.ProductResponse;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;

import java.util.List;

public class ProductHttpResponseMapper {
    private ProductHttpResponseMapper() {}

    public static ProductResponse toResponse(ProductResultDTO dto){
        return new ProductResponse(
                dto.id().getId(),
                dto.name(),
                dto.description(),
                dto.price(),
                dto.status().name(),
                dto.createdAt()
        );
    }

    public static List<ProductResponse> toResponseList(List<ProductResultDTO> dtos){
        return dtos.stream()
                .map(ProductHttpResponseMapper::toResponse)
                .toList();
    }
}
