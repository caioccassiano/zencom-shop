package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;

import java.util.List;

public class ListAllActiveProductsUseCase {
    private final ProductRepository productRepository;
    public ListAllActiveProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResultDTO> getAllActiveProducts() {
        return productRepository.findAllActive()
                .stream()
                .map(ProductResultMapper::toResult)
                .toList();
    }
}
