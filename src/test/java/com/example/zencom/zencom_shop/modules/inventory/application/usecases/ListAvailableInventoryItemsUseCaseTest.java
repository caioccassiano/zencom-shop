package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ListAvailableInventoryItemsUseCaseTest {

    private ListAvailableInventoryItemsUseCase listAvailableInventoryItemsUseCase;
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        listAvailableInventoryItemsUseCase = new ListAvailableInventoryItemsUseCase(inventoryRepository);
    }

    @Test
    void should_return_list_of_available_inventory_items() {
        ProductId productId1 = ProductId.newId();
        ProductId productId2 = ProductId.newId();

        InventoryItem inventoryItem1 = InventoryItem.create(productId1);
        InventoryItem inventoryItem2 = InventoryItem.create(productId2);

        inventoryItem1.addStock(12);
        inventoryItem2.addStock(12);

        when(inventoryRepository.findAllWithAvailableQuantity())
                .thenReturn(List.of(inventoryItem1, inventoryItem2));

        List<InventoryItemResultDTO> result = listAvailableInventoryItemsUseCase.findAllAvailableItems();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(productId1.getId(), result.get(0).productId());
        assertEquals(productId2.getId(), result.get(1).productId());
    }

    @Test
    void should_return_empty_list_when_no_available_inventory_items() {
        ProductId productId1 = ProductId.newId();
        ProductId productId2 = ProductId.newId();

        InventoryItem inventoryItem1 = InventoryItem.create(productId1);
        InventoryItem inventoryItem2 = InventoryItem.create(productId2);

        when(inventoryRepository.findAllWithAvailableQuantity()).thenReturn(List.of(inventoryItem1, inventoryItem2));

        List<InventoryItemResultDTO> result = listAvailableInventoryItemsUseCase.findAllAvailableItems();

        assertNotNull(result);
        assertEquals(result.size(), 0);
    }

}