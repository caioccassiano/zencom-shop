package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.CreateInventoryItemCommand;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class CreateInventoryUseCaseTest {

    private CreateInventoryUseCase createInventoryUseCase;
    private InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    void setUp() {
        inventoryItemRepository = mock(InventoryItemRepository.class);
        createInventoryUseCase = new CreateInventoryUseCase(inventoryItemRepository);
    }

    @Test
    void should_create_inventory_when_not_exists() {
        UUID productId = UUID.randomUUID();
        CreateInventoryItemCommand command = new CreateInventoryItemCommand(productId);

        when(inventoryItemRepository.existsByProductId(ProductId.from_UUID(productId))).thenReturn(false);

        createInventoryUseCase.execute(command);

        verify(inventoryItemRepository, times(1)).existsByProductId(ProductId.from_UUID(productId));
        verify(inventoryItemRepository, times(1)).save(any(InventoryItem.class));
        verifyNoMoreInteractions(inventoryItemRepository);
    }

    @Test
    void should_not_create_inventory_when_exists() {
        UUID productId = UUID.randomUUID();
        CreateInventoryItemCommand command = new CreateInventoryItemCommand(productId);
        when(inventoryItemRepository.existsByProductId(ProductId.from_UUID(productId))).thenReturn(true);
        createInventoryUseCase.execute(command);

        verify(inventoryItemRepository, times(1)).existsByProductId(ProductId.from_UUID(productId));
        verify(inventoryItemRepository,never()).save(any(InventoryItem.class));
        verifyNoMoreInteractions(inventoryItemRepository);
    }

}