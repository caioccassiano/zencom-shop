package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.ReserveStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.application.usecases.stock.ReserveStockUseCase;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReserveStockUseCaseTest {
    private ReserveStockUseCase reserveStockUseCase;
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        reserveStockUseCase = new ReserveStockUseCase(inventoryRepository);
    }

    @Test
    void should_reserve_stock_when_item_is_found() {
        ProductId productId = ProductId.newId();
        int quantity = 3;
        InventoryItem inventoryItem = InventoryItem.create(productId);

        when(this.inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(inventoryItem));

        reserveStockUseCase.execute(new ReserveStockCommandDTO(productId, quantity));

        verify(inventoryRepository).findByProductId(productId);
        verify(inventoryRepository).save(inventoryItem);
        assertEquals(3, inventoryItem.getReservedQuantity());

    }

    @Test
    void should_throw_exception_when_item_is_not_found() {
        ProductId productId = ProductId.newId();
        when(this.inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(InventoryItemNotFoundException.class,
                () -> reserveStockUseCase.execute(
                        new ReserveStockCommandDTO(productId, 0)));
        verify(inventoryRepository).findByProductId(productId);
        verifyNoMoreInteractions(inventoryRepository);
    }
}