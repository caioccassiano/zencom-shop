package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;

public class GetProductByNameUseCase {
    private final ProductRepository productRepository;

    public GetProductByNameUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResultDTO getProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(ProductDoesNotExistException::new);
        return ProductResultMapper.toResult(product);
    }
}



