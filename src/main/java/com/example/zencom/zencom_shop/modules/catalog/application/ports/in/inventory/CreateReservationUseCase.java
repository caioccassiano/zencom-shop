package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CreateReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;

public interface CreateReservationUseCase {
    Reservation execute(CreateReservationCommandDTO command);
}
