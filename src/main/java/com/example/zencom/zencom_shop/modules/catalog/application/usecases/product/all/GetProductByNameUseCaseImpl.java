package com.example.zencom.zencom_shop.modules.catalog.application.usecases.product.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.GetProductByNameUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetProductByNameUseCaseImpl implements GetProductByNameUseCase {
    private final ProductRepository productRepository;

    public GetProductByNameUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResultDTO getProductByName(String name) {
        Product product = productRepository.findByName(name)
                .orElseThrow(ProductDoesNotExistException::new);
        return ProductResultMapper.toResult(product);
    }
}



