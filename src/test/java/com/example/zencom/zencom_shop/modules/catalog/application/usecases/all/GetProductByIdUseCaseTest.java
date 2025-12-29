package com.example.zencom.zencom_shop.modules.catalog.application.usecases.all;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.ProductDoesNotExistException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GetProductByIdUseCaseTest {
    private ProductRepository productRepository;
    private GetProductByIdUseCase useCase;

    @BeforeEach
    void setup() {
        productRepository = mock(ProductRepository.class);
        useCase = new GetProductByIdUseCase(productRepository);
    }

    @Test
    void should_return_product_when_found() {
        ProductId id = ProductId.newId();
        Product product = mock(Product.class);

        when(productRepository.findById(id)).thenReturn(Optional.of(product));
        ProductResultDTO result = useCase.getProductById(id);

        assertNotNull(result);
        verify(productRepository).findById(id);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    void should_throw_exception_when_product_not_found() {

        ProductId id = ProductId.newId();
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductDoesNotExistException.class,
                () -> useCase.getProductById(id));

        verify(productRepository).findById(id);
    }
}
