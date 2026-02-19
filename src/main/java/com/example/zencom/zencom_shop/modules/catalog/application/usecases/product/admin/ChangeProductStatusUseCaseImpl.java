package com.example.zencom.zencom_shop.modules.catalog.application.usecases.product.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.ChangeProductStatusCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.ChangeProductStatusUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangeProductStatusUseCaseImpl implements ChangeProductStatusUseCase {

    private final ProductRepository productRepository;

    public ChangeProductStatusUseCaseImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(ChangeProductStatusCommand dto){
        Product product = productRepository.findById(dto.id()).orElseThrow(ProductDoesNotExistException::new);
        product.changeStatus(dto.newStatus());
        productRepository.save(product);
    }
}
