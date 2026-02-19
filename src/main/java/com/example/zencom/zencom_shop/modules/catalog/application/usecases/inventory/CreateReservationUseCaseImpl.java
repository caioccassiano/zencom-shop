package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CreateReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.CreateReservationUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.ReservationRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CreateReservationUseCaseImpl implements CreateReservationUseCase {
    private final InventoryItemRepository inventoryItemRepository;
    private final ReservationRepository reservationRepository;

    public CreateReservationUseCaseImpl(InventoryItemRepository inventoryItemRepository, ReservationRepository reservationRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.reservationRepository = reservationRepository;
    }

    public Reservation execute(CreateReservationCommandDTO command) {
        validate_input(command);
        Reservation reservation = validateRequestId(command.requestId());
        if(reservation != null) return reservation;
        List<InventoryItem> inventoryItems = loadInventoryItems(command.items());
        checkProductExists(command.items(), inventoryItems);
        Map<UUID, InventoryItem> inventoryByProduct = indexInventory(inventoryItems);
        checkStock(command.items(), inventoryByProduct);
        reserveStock(command.items(), inventoryByProduct);
        reservation = createReservation(command.requestId(), command.customerId(), command.items());
        reservationRepository.save(reservation);
        inventoryItemRepository.saveAll(inventoryItems);
        return reservation;





    }
    //1. Validate function input
    private void validate_input(CreateReservationCommandDTO command) {
        if(command == null) {
            throw new InvalidInputException("Invalid reservation command");
        }
        if ((command.requestId() == null || command.requestId().isEmpty())) {
            throw new InvalidInputException("Invalid reservation request id");
        }
        if (command.customerId() == null) {
            throw new InvalidInputException("Invalid reservation customer id");
        }
        if(command.items().isEmpty()) {
            throw new InvalidInputException("Invalid reservation items");
        }
        for(ReservationItem item : command.items()) {
            if(item.quantity() <= 0) {
                throw new InvalidInputException("Invalid reservation item quantity");
            }
            if(item.productId() == null) {
                throw new InvalidInputException("Invalid reservation item productId");
            }


        }



    }

    //2. Validate there is any reservation with same RequestId
    private Reservation validateRequestId(String requestId) {
        Optional<Reservation> existing = reservationRepository.findByRequestId(requestId);
        if(existing.isEmpty()) return null;

        Reservation reservation = existing.get();

        if (reservation.status() == ReservationStatus.RESERVED) {
            return reservation;
        }
        if (reservation.status() == ReservationStatus.COMMITTED) {
            throw new InvalidInputException("Reservation already committed");
        }
        if(reservation.status() == ReservationStatus.RELEASED) {
            throw new InvalidInputException("Reservation already released");
        }
        return null;
    }

    //3. Fetch InventoryItem for each productId in the request
    private List<InventoryItem> loadInventoryItems(List<ReservationItem> items) {
        List<UUID> productsIds = items.stream()
                .map(ReservationItem::productId).toList();
        return inventoryItemRepository.findProductsByIds(productsIds);
    }

    //4. Check for each productId in the request there is an Inventory for it
    private void checkProductExists(List<ReservationItem> items, List<InventoryItem> inventoryItems) {
        Set<ProductId> existing = inventoryItems.stream()
                .map(InventoryItem::getProductId)
                .collect(Collectors.toSet());
        for(ReservationItem item : items) {
            ProductId productId = ProductId.from_UUID(item.productId());
            if(!existing.contains(productId)) {
                throw new InvalidInputException("Product not stocked " + item.productId());
            }
        }
    }

    //5. It converts a List of InventoryItem in a map to optimize queries
    private Map<UUID, InventoryItem> indexInventory(List<InventoryItem> inventoryItems) {
        Map<UUID, InventoryItem> map = new HashMap<>();
        for(InventoryItem item : inventoryItems){
            UUID productId = item.getProductId().getId();
            map.put(productId, item);
        }
        return map;
    }

    //6. Check every item in the request has avaiable stock
    private void checkStock (List<ReservationItem> items, Map<UUID, InventoryItem> inventoryByProduct) {
        for (ReservationItem item : items) {
            UUID productId = item.productId();
            int quantityRequested = item.quantity();

            InventoryItem inventory = inventoryByProduct.get(productId);
            if(inventory == null) throw new InvalidInputException("Invalid reservation request");
            int avaiable = inventory.getAvailableQuantity();
            if(avaiable < quantityRequested) {
                throw new InvalidInputException("Reservation quantity exceeded");
            }
        }
    }

    //7. This method confirm the stock reservation for each ReservationItem
    private void reserveStock(List<ReservationItem> items, Map<UUID, InventoryItem> inventoryByProduct) {
        for (ReservationItem item : items) {
            UUID productId = item.productId();
            int quantityRequested = item.quantity();
            InventoryItem inventory = inventoryByProduct.get(productId);
            if(inventory==null) throw new InvalidInputException("Reservation Item not found");
            inventory.reserveStock(quantityRequested);
        }
    }

    private Reservation createReservation(String requestIt, UUID customerId, List<ReservationItem> items) {
        return Reservation.create(
                requestIt,
                customerId,
                items
        );


    }
}

