package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CancelReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.inventory.ReservationRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;

import java.util.*;

public class CancelReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public CancelReservationUseCase(ReservationRepository reservationRepository, InventoryItemRepository itemRepository) {
        this.reservationRepository = reservationRepository;
        this.inventoryItemRepository = itemRepository;
    }

    public void execute(CancelReservationCommandDTO command){
        if(command==null) throw new IllegalArgumentException("command cannot be null");
        Reservation reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(()->new IllegalArgumentException("reservation not found"));
        validateIdempotencyKey(reservation);
        List<InventoryItem> inventoryItems = getInventoryItems(reservation);
        Map<UUID, InventoryItem> inventoryByProduct = inventoryByProduct(inventoryItems);
        releaseStock(inventoryByProduct, reservation.items());
        reservation.markAsReleased();
        reservationRepository.save(reservation);
        inventoryItemRepository.saveAll(inventoryItems);
    }

    private void validateIdempotencyKey(Reservation reservation){
        if(reservation.status()== ReservationStatus.RELEASED) {
            return;
        }
        if (reservation.status()==ReservationStatus.COMMITTED) {
            throw new IllegalStateException("reservation is already committed");
        }
    }

    private List<InventoryItem> getInventoryItems(Reservation reservation){
        List<UUID> productsIds = new ArrayList<>();
        for(ReservationItem item : reservation.items()){
            productsIds.add(item.productId());
        }
        return inventoryItemRepository.findProductsByIds(productsIds);
    }

    private Map<UUID, InventoryItem> inventoryByProduct(List<InventoryItem> inventoryItems){
        Map<UUID, InventoryItem> map = new HashMap<>();
        for(InventoryItem item : inventoryItems){
            UUID productId = item.getProductId().getId();
            map.put(productId, item);
        }
        return map;
    }

    private void releaseStock(Map<UUID, InventoryItem> inventoryItems, List<ReservationItem> items){
        for(ReservationItem item : items){
            UUID productId = item.productId();
            int quantity = item.quantity();

            InventoryItem inventory = inventoryItems.get(productId);
            if(inventory==null){
                throw new IllegalStateException("inventory not found");
            }
            inventory.releaseStock(quantity);
        }
    }
}
