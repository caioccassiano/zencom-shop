package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory;

import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.request.IncreaseStockCommandRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.request.ReleaseStockCommandRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.response.InventoryItemAdminResponse;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.mappers.InventoryHttpRequestMapper;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.mappers.InventoryHttpResponseMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.ListInventoryItemsUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.CommitStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.IncreaseStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.ReleaseStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final IncreaseStockUseCase increaseStockUseCase;
    private final ReleaseStockUseCase releaseStockUseCase;
    private final CommitStockUseCase commitStockUseCase;
    private final ListInventoryItemsUseCase listInventoryItemsUseCase;


    /*-------ADMIN ROUTES--------*/
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/increase/{product_id}")
    public ResponseEntity<Void> increaseStock(@RequestBody IncreaseStockCommandRequest request,
                                              @PathVariable("product_id") UUID productId) {
        var cmd = InventoryHttpRequestMapper.toAddStockCommandDTO(request,productId);
        increaseStockUseCase.execute(cmd);
        return ResponseEntity.ok().build();

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/release/{product_id}")
    public  ResponseEntity<Void> releaseStock(@PathVariable("product_id") UUID productId,
                                              @RequestBody ReleaseStockCommandRequest request) {
        var cmd = InventoryHttpRequestMapper.toReleaseStockCommandDTO(request, productId);
        releaseStockUseCase.execute(cmd);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<InventoryItemAdminResponse>> listInventoryItems() {
        return ResponseEntity.status(HttpStatus.OK).body(listInventoryItemsUseCase.execute()
                .stream()
                .map(InventoryHttpResponseMapper::toInventoryItemAdminResponse)
                .toList());
    }
}
