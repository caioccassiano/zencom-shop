package com.example.zencom.zencom_shop.modules.catalog.application.usecases.product.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.GetProductByIdUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {
    private final ProductRepository productRepository;
    public GetProductByIdUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResultDTO getProductById(ProductId id) {
        Product product = productRepository.findById(id).orElseThrow(ProductDoesNotExistException::new);
        return ProductResultMapper.toResult(product);
    }
}


