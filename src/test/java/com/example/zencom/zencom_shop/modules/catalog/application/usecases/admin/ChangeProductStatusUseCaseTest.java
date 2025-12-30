package com.example.zencom.zencom_shop.modules.catalog.application.usecases.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs.ChangeProductStatusCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChangeProductStatusUseCaseTest {
    private ProductRepository productRepository;
    private ChangeProductStatusUseCase useCase;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        useCase = new ChangeProductStatusUseCase(productRepository);
    }

    @Test
    void should_change_status_and_save_when_product_exists() {
        // arrange
        ProductId id = ProductId.newId();
        ProductStatus newStatus = ProductStatus.ACTIVE; // ou INACTIVE, conforme seu enum

        ChangeProductStatusCommand command = new ChangeProductStatusCommand(id, newStatus);

        Product product = mock(Product.class);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        // act
        useCase.execute(command);

        // assert (verify interactions)
        verify(productRepository).findById(id);
        verify(product).changeStatus(newStatus);
        verify(productRepository).save(product);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void should_throw_exception_when_product_does_not_exist() {
        // arrange
        ProductId id = ProductId.newId();
        ProductStatus newStatus = ProductStatus.INACTIVE;

        ChangeProductStatusCommand command = new ChangeProductStatusCommand(id, newStatus);

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // act + assert
        assertThrows(ProductDoesNotExistException.class, () -> useCase.execute(command));

        verify(productRepository).findById(id);
        verify(productRepository, never()).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }

}