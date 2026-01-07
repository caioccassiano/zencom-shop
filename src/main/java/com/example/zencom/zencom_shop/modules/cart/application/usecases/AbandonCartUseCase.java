package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.AbandonCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;

public class AbandonCartUseCase {

    private final CartRepository cartRepository;
    public AbandonCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void execute(AbandonCartCommandDTO commandDTO){
        Cart cart = this.cartRepository.findActiveByUserId(commandDTO.userId())
                .orElseThrow(() -> new CartNotFoundOrINotActiveException("Cart not found"));

        cart.abandon();
        cartRepository.save(cart);
    }
}
