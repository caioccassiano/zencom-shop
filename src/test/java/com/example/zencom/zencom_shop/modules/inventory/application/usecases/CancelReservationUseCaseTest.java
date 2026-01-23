package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.reservation.CancelReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.ReservationRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.Reservation;
import com.example.zencom.zencom_shop.modules.inventory.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.inventory.domain.vo.ReservationItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

class CancelReservationUseCaseTest {
    private CancelReservationUseCase cancelReservationUseCase;
    private ReservationRepository reservationRepository;
    private InventoryItemRepository inventoryItemRepository;

    String requestId = "req-123";
    UUID reservationId = UUID.randomUUID();
    UUID customerId = UUID.randomUUID();
    UUID productId = UUID.randomUUID();
    List<ReservationItem> items = List.of(
            new ReservationItem(
                    productId,
                    5
            )
    );

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        inventoryItemRepository = mock(InventoryItemRepository.class);
        cancelReservationUseCase = new CancelReservationUseCase(reservationRepository, inventoryItemRepository);


    }

    @Test
    void should_cancel_reservation() {
        Reservation reservation = Reservation.create(
                requestId,
                customerId,
                items
        );
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        InventoryItem inventoryItem = InventoryItem.create(
                ProductId.from_UUID(productId)
        );
        when(inventoryItemRepository.findProductsByIds(List.of(productId))).thenReturn(List.of(inventoryItem));

        inventoryItem.addStock(15);
        inventoryItem.reserveStock(6);

        assertEquals(6, inventoryItem.getReservedQuantity());
        assertEquals(9, inventoryItem.getAvailableQuantity());

        cancelReservationUseCase.execute(
                new CancelReservationCommandDTO(
                        reservationId
                )
        );
        assertEquals(ReservationStatus.RELEASED, reservation.status());
        assertEquals(1, inventoryItem.getReservedQuantity());
        assertEquals(14, inventoryItem.getAvailableQuantity());
        verify(reservationRepository, times(1)).findById(reservationId);



    }


}