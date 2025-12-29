package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;

import java.util.List;

public class ListAllProducts {
    private final ProductRepository productRepository;

    public ListAllProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResultDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResultMapper::toResult)
                .toList();
    }
}
