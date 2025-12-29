package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductByNameUseCaseTest {
    private GetProductByNameUseCase getProductByNameUseCase;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        getProductByNameUseCase = new GetProductByNameUseCase(productRepository);
    }

    @Test
    void should_return_product_by_name() {
        String name = "Iphone 17 pro max";
        Product product = mock(Product.class);

        when(productRepository.findByName(name)).thenReturn(Optional.of(product));
        ProductResultDTO productResultDTO = getProductByNameUseCase.getProductByName(name);

        assertNotNull(productResultDTO);
        verify(productRepository).findByName(name);
        verifyNoMoreInteractions(productRepository);

    }

    @Test
    void should_throw_an_exception_when_product_not_found() {
        String name = "Iphone 17 pro max";
        when(productRepository.findByName(name)).thenReturn(Optional.empty());

        assertThrows(ProductDoesNotExistException.class, () -> getProductByNameUseCase.getProductByName(name));
        verify(productRepository).findByName(name);
        verifyNoMoreInteractions(productRepository);

    }

}