package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.GetInventoryItemByIdCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.GetInventoryByIdUseCase;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetInventoryByIdUseCaseTest {
    InventoryItemRepository inventoryItemRepository;
    GetInventoryByIdUseCase getInventoryByIdUseCase;

    @BeforeEach
    void setUp() {
        inventoryItemRepository = mock(InventoryItemRepository.class);
        getInventoryByIdUseCase = new GetInventoryByIdUseCase(inventoryItemRepository);
    }

    @Test
    void should_return_an_item_when_found() {
        UUID productId = UUID.randomUUID();
        InventoryItem item = InventoryItem.create(ProductId.from_UUID(productId));

        when(inventoryItemRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.of(item));

        InventoryItemResultDTO result = getInventoryByIdUseCase.execute(new GetInventoryItemByIdCommand(productId));

        assertNotNull(result);
        verify(inventoryItemRepository).findByProductId(ProductId.from_UUID(productId));
        verifyNoMoreInteractions(inventoryItemRepository);
    }

    @Test
    void should_throw_an_exception_when_not_found() {
        UUID productId = UUID.randomUUID();
        when(inventoryItemRepository.findByProductId(ProductId.from_UUID(productId))).thenReturn(Optional.empty());

        assertThrows(InventoryItemNotFoundException.class, () -> getInventoryByIdUseCase.execute(new GetInventoryItemByIdCommand(productId)));
        verify(inventoryItemRepository).findByProductId(ProductId.from_UUID(productId));
        verifyNoMoreInteractions(inventoryItemRepository);
    }

}