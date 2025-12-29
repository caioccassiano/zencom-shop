package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListAllActiveProductsUseCaseTest {

    private ListAllActiveProductsUseCase listAllActiveProductsUseCase;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        listAllActiveProductsUseCase = new ListAllActiveProductsUseCase(productRepository);
    }

    @Test
    void should_return_all_active_products() {
        Product p1 = mock(Product.class);
        Product p2 = mock(Product.class);
        Product p3 = mock(Product.class);

        when(productRepository.findAllActive()).thenReturn(List.of(p1, p2, p3));

        List<ProductResultDTO> result = listAllActiveProductsUseCase.getAllActiveProducts();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(productRepository).findAllActive();
        verifyNoMoreInteractions(productRepository);


    }

    @Test
    void should_return_empty_list_when_all_products_are_inactive() {
        when(productRepository.findAllActive()).thenReturn(List.of());
        List<ProductResultDTO> result = listAllActiveProductsUseCase.getAllActiveProducts();

        assertNotNull(result);
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
        verify(productRepository).findAllActive();
        verifyNoMoreInteractions(productRepository);

    }

}