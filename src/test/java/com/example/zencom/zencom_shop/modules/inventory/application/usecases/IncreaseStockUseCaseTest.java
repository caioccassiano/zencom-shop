package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncreaseStockUseCaseTest {
    private IncreaseStockUseCase useCase;
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        useCase = new IncreaseStockUseCase(inventoryRepository);
    }

    @Test
    void should_add_stock_when_product_is_found() {
        ProductId productId = ProductId.newId();
        var quantity = 100;
        AddStockCommandDTO commandDTO = new AddStockCommandDTO(productId, quantity);
        InventoryItem inventoryItem = InventoryItem.create(productId);

        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventoryItem));
        useCase.execute(commandDTO);

        verify(inventoryRepository).findByProductId(productId);
        verify(inventoryRepository).save(inventoryItem);
        assertEquals(100, inventoryItem.getAvailableQuantity());
    }

    @Test
    void should_throw_exception_when_product_is_not_found() {
        ProductId productId = ProductId.newId();
        var quantity = 100;
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());
        assertThrows(InventoryItemNotFoundException.class,
                ()-> useCase.execute(new AddStockCommandDTO(productId, quantity)));
        verify(inventoryRepository).findByProductId(productId);
        verifyNoMoreInteractions(inventoryRepository);
    }
}