package com.example.zencom.zencom_shop.modules.catalog.application.usecases.product.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.UpdateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.UpdateProductUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;

    public UpdateProductUseCaseImpl(ProductRepository productRepository) {
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
