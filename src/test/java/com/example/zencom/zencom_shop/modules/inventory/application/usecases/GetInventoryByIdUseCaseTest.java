package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.GetInventoryItemByIdCommand;
import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetInventoryByIdUseCaseTest {
    InventoryRepository inventoryRepository;
    GetInventoryByIdUseCase getInventoryByIdUseCase;

    @BeforeEach
    void setUp() {
        inventoryRepository = mock(InventoryRepository.class);
        getInventoryByIdUseCase = new GetInventoryByIdUseCase(inventoryRepository);
    }

    @Test
    void should_return_an_item_when_found() {
        ProductId productId = ProductId.newId();
        InventoryItem item = InventoryItem.create(productId);

        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.of(item));

        InventoryItemResultDTO result = getInventoryByIdUseCase.execute(new GetInventoryItemByIdCommand(productId.asString()));

        assertNotNull(result);
        verify(inventoryRepository).findByProductId(productId);
        verifyNoMoreInteractions(inventoryRepository);
    }

    @Test
    void should_throw_an_exception_when_not_found() {
        ProductId productId = ProductId.newId();
        when(inventoryRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(InventoryItemNotFoundException.class, () -> getInventoryByIdUseCase.execute(new GetInventoryItemByIdCommand(productId.asString())));
        verify(inventoryRepository).findByProductId(productId);
        verifyNoMoreInteractions(inventoryRepository);
    }

}