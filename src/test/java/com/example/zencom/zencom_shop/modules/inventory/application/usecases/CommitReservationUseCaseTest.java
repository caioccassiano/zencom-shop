package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CommitReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.inventory.ReservationRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.CommitReservationUseCase;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommitReservationUseCaseTest {

    private CommitReservationUseCase commitReservationUseCase;
    private ReservationRepository reservationRepository;
    private InventoryItemRepository inventoryItemRepository;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        inventoryItemRepository = mock(InventoryItemRepository.class);
        commitReservationUseCase = new CommitReservationUseCase(reservationRepository, inventoryItemRepository);
    }

    @Test
    void should_commit_reservation() {
        UUID reservationId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Reservation reservation = Reservation.create(
                "123",
                UUID.randomUUID(),
                List.of(
                        new ReservationItem(
                                productId,
                                3
                        )
                )
        );when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));


        InventoryItem inventoryItem = InventoryItem.create(ProductId.from_UUID(productId));

        when(inventoryItemRepository.findProductsByIds(List.of(productId))).thenReturn(List.of(inventoryItem));
        inventoryItem.addStock(10);
        inventoryItem.reserveStock(4);

        commitReservationUseCase.execute(
                new CommitReservationCommandDTO(
                        reservationId
                )
        );

        assertEquals(1, inventoryItem.getReservedQuantity());
        assertEquals(6, inventoryItem.getAvailableQuantity());
        assertEquals(ReservationStatus.COMMITTED, reservation.status());

    }

    @Test
    void should_not_commit_reservation_already_released() {
        UUID reservationId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Reservation reservation = Reservation.create(
                "123",
                UUID.randomUUID(),
                List.of(
                        new ReservationItem(
                                productId,
                                3
                        )
                )
        );
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        reservation.markAsReleased();
        assertThrows(IllegalArgumentException.class, () -> commitReservationUseCase.execute(
                new CommitReservationCommandDTO(
                        reservationId
                )
        ));


    }

    @Test
    void should_not_commit_reservation_already_committed() {
        UUID reservationId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Reservation existing = Reservation.create(
                "123",
                UUID.randomUUID(),
                List.of(
                        new ReservationItem(
                                productId,
                                3
                        )
                )
        );
        existing.markAsCommitted();
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(existing));
        commitReservationUseCase.execute(
                new CommitReservationCommandDTO(
                        reservationId
                )
        );

        verify(inventoryItemRepository,never()).findProductsByIds(List.of(productId));
        verify(inventoryItemRepository,never()).saveAll(any());
        verify(reservationRepository,never()).save(any());
    }
}