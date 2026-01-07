package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.UpdateCartItemQuantityCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.dtos.output.CartResultDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.exception.InvalidInputException;
import com.example.zencom.zencom_shop.modules.cart.application.mappers.CartResultMapper;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.UUID;

public class UpdateCartItemQuantityUseCase {
    private final CartRepository cartRepository;

    public UpdateCartItemQuantityUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartResultDTO execute(UpdateCartItemQuantityCommandDTO command) {
        validateCommonFields(
                command.userId(),
                command.productId());
        Cart cart = this.cartRepository.findActiveByUserId(command.userId())
                .orElseThrow(() -> new CartNotFoundOrINotActiveException("Cart not found"));
        ProductId id = ProductId.from_UUID(command.productId());
        if (command.quantity() == 0) {
            cart.removeItem(id);
        } else {
            cart.updateQuantity(id, command.quantity());

        }
        cartRepository.save(cart);
        return CartResultMapper.toDto(cart);
    }

    private void validateCommonFields(UUID userId, UUID productId) {
        if (userId == null) throw new InvalidInputException("userId");
        if (productId == null) throw new InvalidInputException("productId");
    }

}
