package com.example.zencom.zencom_shop.modules.catalog.application.usecases.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs.CreateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;

import static com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper.toResult;

public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResultDTO create (CreateProductCommand dto) {
      Product product = Product.create(dto.name(), dto.description(), dto.price());
      this.productRepository.save(product);
      return toResult(product);

    };
}
