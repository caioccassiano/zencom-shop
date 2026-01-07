package com.example.zencom.zencom_shop.modules.cart.application.mappers;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.output.CartItemResultDTO;
import com.example.zencom.zencom_shop.modules.cart.application.dtos.output.CartResultDTO;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.CartItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CartResultMapper {
    public static CartResultDTO toDto(Cart cart) {
        List<CartItemResultDTO> cartItems = cart.getCartItemList()
                .stream()
                .map(CartResultMapper::toItemDto)
                .toList();
        return new CartResultDTO(
                cart.getCartId().getId(),
                cart.getUserId(),
                cart.getStatus(),
                cartItems,
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }

    private static CartItemResultDTO toItemDto(CartItem item){
        return new CartItemResultDTO(
                item.getProductId().getId(),
                item.getQuantity(),
                item.getUnitPrice(),
                item.getAddedAt()
        );
    }
}
