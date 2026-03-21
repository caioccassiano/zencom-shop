package com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.mappers;

import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.response.CartItemResponse;
import com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.response.CartResponse;
import com.example.zencom.zencom_shop.modules.cart.application.dtos.output.CartResultDTO;

public class CartHttpResponseMapper {
    private CartHttpResponseMapper() {}

    public static CartResponse toResponse(CartResultDTO dto) {
        return new CartResponse(
                dto.cartId(),
                dto.userId(),
                dto.status(),
                dto.items().stream()
                        .map(i -> new CartItemResponse(i.productId(), i.quantity(), i.price(), i.addedAt()))
                        .toList(),
                dto.createdAt(),
                dto.updatedAt()
        );
    }
}
