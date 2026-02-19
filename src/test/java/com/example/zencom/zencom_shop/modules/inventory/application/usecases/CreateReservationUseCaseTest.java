package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CreateReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.ReservationRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.CreateReservationUseCase;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateReservationUseCaseTest {

    private InventoryItemRepository inventoryItemRepository;
    private ReservationRepository reservationRepository;

    private CreateReservationUseCase createReservationUseCase;

    @BeforeEach
    void setUp() {
        inventoryItemRepository = mock(InventoryItemRepository.class);
        reservationRepository = mock(ReservationRepository.class);
        createReservationUseCase = new CreateReservationUseCase(inventoryItemRepository, reservationRepository);

    }

    @Test
    void should_create_reservation() {
        String requestId = "123";
        UUID productId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        CreateReservationCommandDTO command = new CreateReservationCommandDTO(
                requestId,
                customerId,
                List.of(new ReservationItem(productId, 2))
        );
        when(reservationRepository.findByRequestId(requestId)).thenReturn(Optional.empty());

        InventoryItem inventory = InventoryItem.create(ProductId.from_UUID(productId));
        inventory.addStock(10);

        when(inventoryItemRepository.findProductsByIds(List.of(productId))).thenReturn(List.of(inventory));

        Reservation reservation = createReservationUseCase.execute(command);

        assertNotNull(reservation);
        assertEquals(ReservationStatus.RESERVED, reservation.status());
        assertEquals(8, inventory.getAvailableQuantity()); // 10 - 2
        assertEquals(2, inventory.getReservedQuantity());  // 0 + 2

        verify(reservationRepository).save(any(Reservation.class));
        verify(inventoryItemRepository).saveAll(List.of(inventory));


    }

    @Test
    void should_return_a_reservation_when_same_requestId() {
        String requestId = "123";
        UUID productId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        Reservation existingReservation = Reservation.create(
                requestId,
                customerId,
                List.of(
                        new ReservationItem(
                                productId,
                                3
                        )
                )
        );
        when(reservationRepository.findByRequestId(requestId)).thenReturn(Optional.of(existingReservation));

        CreateReservationCommandDTO command = new CreateReservationCommandDTO(
                requestId,
                customerId,
                List.of(new ReservationItem(productId, 2))
        );

        Reservation result = createReservationUseCase.execute(command);
        assertNotNull(result);
        assertSame(existingReservation, result);
        verify(reservationRepository, never()).save(any());
        verify(inventoryItemRepository, never()).findProductsByIds(any());



    }

    @Test
    void should_throw_exception_when_not_enough_available_quantity() {
        String requestId = "123";
        UUID productId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();

        CreateReservationCommandDTO command = new CreateReservationCommandDTO(
                requestId,
                customerId,
                List.of(new ReservationItem(productId, 20))
        );
        when(reservationRepository.findByRequestId(requestId)).thenReturn(Optional.empty());

        InventoryItem inventoryItem = InventoryItem.create(ProductId.from_UUID(productId));
        inventoryItem.addStock(10);


        assertThrows(InvalidInputException.class, () -> createReservationUseCase.execute(command));
    }


}