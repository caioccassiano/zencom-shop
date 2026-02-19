package com.example.zencom.zencom_shop.modules.catalog.application.usecases.product.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.ListAllActiveProductsUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListAllActiveProductsUseCaseImpl implements ListAllActiveProductsUseCase {
    private final ProductRepository productRepository;
    public ListAllActiveProductsUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public List<ProductResultDTO> getAllActiveProducts() {
        return productRepository.findAllActive()
                .stream()
                .map(ProductResultMapper::toResult)
                .toList();
    }
}
