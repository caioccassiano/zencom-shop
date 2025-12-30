package com.example.zencom.zencom_shop.modules.catalog.application.usecases.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs.UpdateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateProductUseCaseTest {

    private UpdateProductUseCase updateProductUseCase;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        updateProductUseCase = new UpdateProductUseCase(productRepository);
    }

    @Test
    void should_update_product_when_found() {
        ProductId id = ProductId.newId();
        UpdateProductCommand command = new UpdateProductCommand(
                id,
                "newName",
                "newDescription",
                new BigDecimal("199.90")
        );
        Product product = mock(Product.class);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        ProductResultDTO result = updateProductUseCase.update(command);

        assertNotNull(result);
        verify(productRepository).findById(id);
        verify(product).update("newName", "newDescription", new BigDecimal("199.90"));
        verify(productRepository).save(product);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void should_throw_exception_when_product_not_found() {
        ProductId id = ProductId.newId();
        UpdateProductCommand command = new UpdateProductCommand(
                id,
                "New name",
                "New description",
                new BigDecimal("199.90")
        );

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductDoesNotExistException.class, () -> updateProductUseCase.update(command));

        verify(productRepository).findById(id);
        verifyNoMoreInteractions(productRepository);
    }

}