package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CommitReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.CommitReservationUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.CommitStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.ReservationRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class CommitReservationUseCaseImpl implements CommitReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final InventoryItemRepository inventoryItemRepository;

    public CommitReservationUseCaseImpl(ReservationRepository reservationRepository, InventoryItemRepository itemRepository) {
        this.reservationRepository = reservationRepository;
        this.inventoryItemRepository = itemRepository;
    }

    public void execute(CommitReservationCommandDTO command) {
        if(command==null) throw new IllegalArgumentException("input cannot be null");
        Reservation reservation = reservationRepository.findById(command.reservationId())
                .orElseThrow(() -> new IllegalArgumentException("reservation not found"));
        if(validateIdempotence(reservation)) return;
        List<InventoryItem> inventoryItems = getInventoryItems(reservation);
        Map<UUID, InventoryItem> inventoryByProduct = indexInventory(inventoryItems);
        commitStock(inventoryByProduct, reservation.items());
        reservation.markAsCommitted();
        reservationRepository.save(reservation);
        inventoryItemRepository.saveAll(inventoryItems);




    }
    private boolean validateIdempotence(Reservation reservation) {
        if(reservation.status()== ReservationStatus.COMMITTED) return true;

        if(reservation.status()==ReservationStatus.RELEASED) throw new IllegalArgumentException("reservation is already released");
        return false;

    }

    private List<InventoryItem> getInventoryItems(Reservation reservation) {
        List<UUID> productsIds = new ArrayList<>();

        for(ReservationItem item : reservation.items()){
            productsIds.add(item.productId());
        }
        return inventoryItemRepository.findProductsByIds(productsIds);
    }

    private Map<UUID,InventoryItem> indexInventory(List<InventoryItem> inventoryItems) {
        Map<UUID,InventoryItem> inventory = new HashMap<>();
        for(InventoryItem item : inventoryItems){
            UUID productId = item.getProductId().getId();
            inventory.put(productId, item);
        }
        return inventory;
    }

    private void commitStock(Map<UUID, InventoryItem> inventoryByProduct, List<ReservationItem> items) {
        for (ReservationItem item : items){
            int quantity = item.quantity();

            InventoryItem inventory = inventoryByProduct.get(item.productId());
            if(inventory==null){
                throw new IllegalArgumentException("inventory not found");
            }
            inventory.commit(quantity);
        }

    }

}
