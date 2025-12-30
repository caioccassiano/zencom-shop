package com.example.zencom.zencom_shop.modules.catalog.application.usecases.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs.CreateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.catalog.domain.exceptions.InvalidPriceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateProductUseCaseTest {

    private CreateProductUseCase createProductUseCase;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        createProductUseCase = new CreateProductUseCase(productRepository);
    }

    @Test
    void should_create_product_and_return_result() {
        CreateProductCommand command = new CreateProductCommand(
                "Iphone 17 pro max",
                "New Iphone",
                new BigDecimal("1499.90")
        );
        ProductResultDTO result = createProductUseCase.create(command);
        assertNotNull(result);
        verify(productRepository, times(1)).save(any(Product.class));
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void should_save_product_with_correct_data() {
        CreateProductCommand command = new CreateProductCommand(
                "MacBook Pro",
                "M3 chip",
                new BigDecimal("12999.00")
        );

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);

        createProductUseCase.create(command);

        verify(productRepository).save(captor.capture());

        Product saved = captor.getValue();

        assertEquals("MacBook Pro", saved.getName());
        assertEquals("M3 chip", saved.getDescription());
        assertEquals(new BigDecimal("12999.00"), saved.getPrice());
    }

    @Test
    void should_throw_when_price_is_invalid() {
        CreateProductCommand command = new CreateProductCommand(
                "Cheap thing",
                "Invalid price",
                new BigDecimal("-1.00")
        );

        assertThrows(InvalidPriceException.class, () -> createProductUseCase.create(command)); // ajuste nome do método
        verifyNoInteractions(productRepository);
    }

}