package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.CreateInventoryItemCommand;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateInventoryUseCaseTest {

    private CreateInventoryUseCase createInventoryUseCase;
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        createInventoryUseCase = new CreateInventoryUseCase(inventoryRepository);
    }

    @Test
    void should_create_inventory_when_not_exists() {
        UUID productId = UUID.randomUUID();
        CreateInventoryItemCommand command = new CreateInventoryItemCommand(productId);

        when(inventoryRepository.existsByProductId(ProductId.from_UUID(productId))).thenReturn(false);

        createInventoryUseCase.execute(command);

        verify(inventoryRepository, times(1)).existsByProductId(ProductId.from_UUID(productId));
        verify(inventoryRepository, times(1)).save(any(InventoryItem.class));
        verifyNoMoreInteractions(inventoryRepository);
    }

    @Test
    void should_not_create_inventory_when_exists() {
        UUID productId = UUID.randomUUID();
        CreateInventoryItemCommand command = new CreateInventoryItemCommand(productId);
        when(inventoryRepository.existsByProductId(ProductId.from_UUID(productId))).thenReturn(true);
        createInventoryUseCase.execute(command);

        verify(inventoryRepository, times(1)).existsByProductId(ProductId.from_UUID(productId));
        verify(inventoryRepository,never()).save(any(InventoryItem.class));
        verifyNoMoreInteractions(inventoryRepository);
    }

}