package com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart;

import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.request.AddItemRequest;
import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.request.UpdateItemQuantityRequest;
import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.response.CartResponse;
import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.mappers.CartHttpRequestMapper;
import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.mappers.CartHttpResponseMapper;
import com.example.zencom.zencom_shop.modules.cart.application.usecases.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final GetOrCreateCartUseCase getOrCreateCartUseCase;
    private final AddItemToCartUseCase addItemToCartUseCase;
    private final UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase;
    private final ClearCartUseCase clearCartUseCase;

    @GetMapping
    public ResponseEntity<CartResponse> getOrCreate(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        var cmd = CartHttpRequestMapper.toGetOrCreateCommand(userId);
        var result = getOrCreateCartUseCase.execute(cmd);
        return ResponseEntity.ok(CartHttpResponseMapper.toResponse(result));
    }

    @PostMapping("/items")
    public ResponseEntity<Void> addItem(
            Authentication authentication,
            @RequestBody @Valid AddItemRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        var cmd = CartHttpRequestMapper.toAddItemCommand(userId, request);
        addItemToCartUseCase.execute(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/items/{productId}")
    public ResponseEntity<CartResponse> updateItemQuantity(
            Authentication authentication,
            @PathVariable UUID productId,
            @RequestBody @Valid UpdateItemQuantityRequest request
    ) {
        UUID userId = UUID.fromString(authentication.getName());
        var cmd = CartHttpRequestMapper.toUpdateQuantityCommand(userId, productId, request);
        var result = updateCartItemQuantityUseCase.execute(cmd);
        return ResponseEntity.ok(CartHttpResponseMapper.toResponse(result));
    }

    @DeleteMapping
    public ResponseEntity<Void> clear(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        var cmd = CartHttpRequestMapper.toClearCartCommand(userId);
        clearCartUseCase.execute(cmd);
        return ResponseEntity.noContent().build();
    }
}
