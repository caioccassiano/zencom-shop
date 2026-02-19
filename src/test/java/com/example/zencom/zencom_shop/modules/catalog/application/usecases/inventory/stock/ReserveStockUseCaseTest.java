package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.stock;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.ReserveStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReserveStockUseCaseTest {
    private ReserveStockUseCase reserveStockUseCase;
    private InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    void setUp() {
        inventoryItemRepository = mock(InventoryItemRepository.class);
        reserveStockUseCase = new ReserveStockUseCase(inventoryItemRepository);
    }

    @Test
    void should_reserve_stock_when_item_is_found() {
       UUID productId = UUID.randomUUID();
        int quantity = 3;
        InventoryItem inventoryItem = InventoryItem.create(ProductId.from_UUID(productId));

        when(this.inventoryItemRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.of(inventoryItem));

        reserveStockUseCase.execute(new ReserveStockCommandDTO(productId, quantity));

        verify(inventoryItemRepository).findByProductId(ProductId.from_UUID(productId));
        verify(inventoryItemRepository).save(inventoryItem);
        assertEquals(3, inventoryItem.getReservedQuantity());

    }

    @Test
    void should_throw_exception_when_item_is_not_found() {
        UUID productId = UUID.randomUUID();
        when(this.inventoryItemRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.empty());

        assertThrows(InventoryItemNotFoundException.class,
                () -> reserveStockUseCase.execute(
                        new ReserveStockCommandDTO(productId, 0)));
        verify(inventoryItemRepository).findByProductId(ProductId.from_UUID(productId));
        verifyNoMoreInteractions(inventoryItemRepository);
    }
}