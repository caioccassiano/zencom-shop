package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListAllProductsUseCaseTest {

    private ListAllProductsUseCase listAllProductsUseCase;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository= mock(ProductRepository.class);
        listAllProductsUseCase = new ListAllProductsUseCase(productRepository);
    }
    @Test
    void should_return_all_products() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);
        Product product3 = mock(Product.class);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2, product3));
        List<ProductResultDTO> result = listAllProductsUseCase.getAll();

        assertNotNull(result);
        assertEquals(3, result.size());
        verify(productRepository, times(1)).findAll();
        verifyNoMoreInteractions(productRepository);

    }
}