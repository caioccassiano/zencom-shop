package com.example.zencom.zencom_shop.modules.catalog.application.usecases.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs.ChangeProductStatusCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;

public class ChangeProductStatusUseCase {

    private final ProductRepository productRepository;

    public ChangeProductStatusUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(ChangeProductStatusCommand dto){
        Product product = productRepository.findById(dto.id()).orElseThrow(ProductDoesNotExistException::new);
        product.changeStatus(dto.newStatus());
        productRepository.save(product);
    }
}
