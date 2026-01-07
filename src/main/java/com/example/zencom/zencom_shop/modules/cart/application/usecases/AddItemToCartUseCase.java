package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.AddItemToCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.exception.InvalidInputException;
import com.example.zencom.zencom_shop.modules.cart.application.exception.ProductNotFoundException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.application.ports.catalog.CatalogPort;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.UUID;

public class AddItemToCartUseCase {
    private final CartRepository cartRepository;
    private final CatalogPort catalogPort;

    public AddItemToCartUseCase(CartRepository cartRepository, CatalogPort catalogPort) {
        this.cartRepository = cartRepository;
        this.catalogPort = catalogPort;
    }

    public void execute(AddItemToCartCommandDTO command) {
        validateInput(command);
        Cart cart = this.cartRepository.findActiveByUserId(command.userId())
                .orElseThrow(() -> new CartNotFoundOrINotActiveException("Cart not found"));
        var snapshot = validateProduct(command.productId());
        cart.addNewItem(snapshot.productId(), command.quantity(), snapshot.unitPrice());
        cartRepository.save(cart);
    }

    private CatalogPort.ProductSnapshot validateProduct(UUID productId) {
        return this.catalogPort.findActiveProduct(ProductId.from_UUID(productId))
                .orElseThrow(() -> new ProductNotFoundException("Product not found or not active"));
    }

    private void validateInput(AddItemToCartCommandDTO command) {
        if(command.userId() == null){
            throw new InvalidInputException("userId");
        }
        if(command.productId() == null){
            throw new InvalidInputException("productId");
        }
        if(command.quantity()<=0){
            throw new InvalidInputException("quantity");
        }
    }
}
