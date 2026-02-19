package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CancelReservationCommandDTO;

public interface CancelReservationUseCase {
    void execute(CancelReservationCommandDTO command);
}
