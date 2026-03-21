package com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.mappers;

import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.request.AddItemRequest;
import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.request.UpdateItemQuantityRequest;
import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.*;

import java.util.UUID;

public final class CartHttpRequestMapper {
    private CartHttpRequestMapper() {}

    public static GetOrCreateCartCommandDTO toGetOrCreateCommand(UUID userId) {
        return new GetOrCreateCartCommandDTO(userId);
    }

    public static AddItemToCartCommandDTO toAddItemCommand(UUID userId, AddItemRequest request) {
        return new AddItemToCartCommandDTO(userId, request.productId(), request.quantity());
    }

    public static UpdateCartItemQuantityCommandDTO toUpdateQuantityCommand(UUID userId, UUID productId, UpdateItemQuantityRequest request) {
        return new UpdateCartItemQuantityCommandDTO(productId, userId, request.quantity());
    }

    public static ClearCartCommandDTO toClearCartCommand(UUID userId) {
        return new ClearCartCommandDTO(userId);
    }
}
