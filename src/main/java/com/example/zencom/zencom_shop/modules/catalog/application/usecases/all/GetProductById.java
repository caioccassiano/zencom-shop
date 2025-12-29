package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class GetProductById {
    private final ProductRepository productRepository;
    public GetProductById(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResultDTO getProductById(ProductId id) {
        Product product = productRepository.findById(id).orElseThrow(ProductDoesNotExistException::new);
        return ProductResultMapper.toResult(product);
    }
}


