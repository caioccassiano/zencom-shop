package com.example.zencom.zencom_shop.modules.catalog.application.usecases.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs.CreateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductIntegrationEventMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;

import java.util.Optional;

import static com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper.toResult;

public class CreateProductUseCase {

    private final ProductRepository productRepository;
    private final IntegrationEventEmitter integrationEventEmitter;

    public CreateProductUseCase(
            ProductRepository productRepository,
            IntegrationEventEmitter integrationEventEmitter) {
        this.productRepository = productRepository;
        this.integrationEventEmitter = integrationEventEmitter;
    }

    public ProductResultDTO create (CreateProductCommand dto) {
      Product product = Product.create(dto.name(), dto.description(), dto.price());
      this.productRepository.save(product);
      integrationEventEmitter.emitFrom(product);
      return toResult(product);

    };


}
