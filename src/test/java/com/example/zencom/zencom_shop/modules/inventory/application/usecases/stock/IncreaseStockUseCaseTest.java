package com.example.zencom.zencom_shop.modules.inventory.application.usecases.stock;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

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
        UUID productId = UUID.randomUUID();
        var quantity = 100;
        AddStockCommandDTO commandDTO = new AddStockCommandDTO(productId, quantity);
        InventoryItem inventoryItem = InventoryItem.create(ProductId.from_UUID(productId));

        when(inventoryRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.of(inventoryItem));
        useCase.execute(commandDTO);

        verify(inventoryRepository).findByProductId(ProductId.from_UUID(productId));
        verify(inventoryRepository).save(inventoryItem);
        assertEquals(100, inventoryItem.getAvailableQuantity());
    }

    @Test
    void should_throw_exception_when_product_is_not_found() {
        UUID productId = UUID.randomUUID();
        var quantity = 100;
        when(inventoryRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.empty());
        assertThrows(InventoryItemNotFoundException.class,
                ()-> useCase.execute(new AddStockCommandDTO(productId, quantity)));
        verify(inventoryRepository).findByProductId(ProductId.from_UUID(productId));
        verifyNoMoreInteractions(inventoryRepository);
    }
}