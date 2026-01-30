package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.stock;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncreaseStockUseCaseTest {
    private IncreaseStockUseCase useCase;
    private InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    void setUp() {
        inventoryItemRepository = mock(InventoryItemRepository.class);
        useCase = new IncreaseStockUseCase(inventoryItemRepository);
    }

    @Test
    void should_add_stock_when_product_is_found() {
        UUID productId = UUID.randomUUID();
        var quantity = 100;
        AddStockCommandDTO commandDTO = new AddStockCommandDTO(productId, quantity);
        InventoryItem inventoryItem = InventoryItem.create(ProductId.from_UUID(productId));

        when(inventoryItemRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.of(inventoryItem));
        useCase.execute(commandDTO);

        verify(inventoryItemRepository).findByProductId(ProductId.from_UUID(productId));
        verify(inventoryItemRepository).save(inventoryItem);
        assertEquals(100, inventoryItem.getAvailableQuantity());
    }

    @Test
    void should_throw_exception_when_product_is_not_found() {
        UUID productId = UUID.randomUUID();
        var quantity = 100;
        when(inventoryItemRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.empty());
        assertThrows(InventoryItemNotFoundException.class,
                ()-> useCase.execute(new AddStockCommandDTO(productId, quantity)));
        verify(inventoryItemRepository).findByProductId(ProductId.from_UUID(productId));
        verifyNoMoreInteractions(inventoryItemRepository);
    }
}