package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.GetOrCreateCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.dtos.output.CartResultDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.InvalidInputException;
import com.example.zencom.zencom_shop.modules.cart.application.mappers.CartResultMapper;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GetOrCreateCartUseCase {
    private final CartRepository cartRepository;

    public GetOrCreateCartUseCase(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public CartResultDTO execute (GetOrCreateCartCommandDTO command){
        if(command.userId() == null){
            throw new InvalidInputException("userId is required");
        }
        Cart cart = cartRepository.findActiveByUserId(command.userId())
                .orElseGet(()-> cartRepository.save(Cart.create(command.userId())));
        return CartResultMapper.toDto(cart);


    }

}
