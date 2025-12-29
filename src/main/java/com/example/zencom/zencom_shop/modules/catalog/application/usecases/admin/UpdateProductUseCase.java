package com.example.zencom.zencom_shop.modules.catalog.application.usecases.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs.UpdateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;

public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResultDTO update(UpdateProductCommand dto) {
        Product product = productRepository.findById(dto.id())
                .orElseThrow(ProductDoesNotExistException::new);
        product.update(
                dto.name(),
                dto.description(),
                dto.price());
        productRepository.save(product);
        return ProductResultMapper.toResult(product);
    }
}
