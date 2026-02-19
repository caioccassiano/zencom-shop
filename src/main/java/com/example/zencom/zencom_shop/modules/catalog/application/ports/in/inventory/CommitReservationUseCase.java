package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CommitReservationCommandDTO;

public interface CommitReservationUseCase {
    void execute(CommitReservationCommandDTO command);
}
