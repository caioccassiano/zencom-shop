package com.example.zencom.zencom_shop.modules.catalog.application.usecases.product.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.ListAllProductsUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListAllProductsUseCaseImpl implements ListAllProductsUseCase {
    private final ProductRepository productRepository;

    public ListAllProductsUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResultDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResultMapper::toResult)
                .toList();
    }
}
