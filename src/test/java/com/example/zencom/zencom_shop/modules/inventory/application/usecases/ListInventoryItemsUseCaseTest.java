package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListInventoryItemsUseCaseTest {
    ListInventoryItemsUseCase listInventoryItemsUseCase;
    InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    void setUp() {
        inventoryItemRepository = mock(InventoryItemRepository.class);
        listInventoryItemsUseCase = new ListInventoryItemsUseCase(inventoryItemRepository);
    }

    @Test
    void should_return_a_list_of_inventory_items() {

        ProductId productId1 = ProductId.newId();
        ProductId productId2 = ProductId.newId();

        InventoryItem item1 = mock(InventoryItem.class);
        InventoryItem item2 = mock(InventoryItem.class);

        when(inventoryItemRepository.findAll()).thenReturn(List.of(item1, item2));

        when(item1.getProductId()).thenReturn(productId1);
        when(item1.getAvailableQuantity()).thenReturn(10);
        when(item1.getReservedQuantity()).thenReturn(2);

        when(item2.getProductId()).thenReturn(productId2);
        when(item2.getAvailableQuantity()).thenReturn(5);
        when(item2.getReservedQuantity()).thenReturn(1);

        List <InventoryItemResultDTO> result =  listInventoryItemsUseCase.execute();

        assertEquals(2, result.size());
        assertEquals(item1.getProductId().toString(), result.get(0).productId());
        assertEquals(10, result.get(0).availableQuantity());
        assertEquals(2, result.get(0).reservedQuantity());

        assertEquals(item2.getProductId().toString(), result.get(1).productId());
        assertEquals(5, result.get(1).availableQuantity());
        assertEquals(1, result.get(1).reservedQuantity());
        verify(inventoryItemRepository).findAll();
        verifyNoMoreInteractions(inventoryItemRepository);
    }
}