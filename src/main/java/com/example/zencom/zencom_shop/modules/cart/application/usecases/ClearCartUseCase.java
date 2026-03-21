package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.ClearCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.vo.CartId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ClearCartUseCase {
    private final CartRepository cartRepository;

    public ClearCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void execute(ClearCartCommandDTO commandDTO) {
        Cart cart = this.cartRepository.findActiveByUserId(commandDTO.userId())
                .orElseThrow(() -> new CartNotFoundOrINotActiveException("Cart not found"));
        cart.clearCart();
        cartRepository.save(cart);
    }
}
