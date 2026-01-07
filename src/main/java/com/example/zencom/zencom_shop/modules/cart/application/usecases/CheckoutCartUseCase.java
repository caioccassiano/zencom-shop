package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.CheckoutCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;

import java.util.UUID;

public class CheckoutCartUseCase {
    private final CartRepository cartRepository;

    public CheckoutCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void execute(CheckoutCartCommandDTO commandDTO) {
        Cart cart = this.cartRepository.findActiveByUserId(commandDTO.userId())
                .orElseThrow(() -> new CartNotFoundOrINotActiveException("Cart not found"));
        cart.checkout();
        cartRepository.save(cart);
    }


}
