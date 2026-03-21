package com.example.zencom.zencom_shop.modules.cart.adapters.out.catalog;

import com.example.zencom.zencom_shop.modules.cart.application.ports.catalog.CatalogPort;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CatalogPortAdapter implements CatalogPort {

    private final ProductRepository productRepository;

    public CatalogPortAdapter(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductSnapshot> findActiveProduct(ProductId productId) {
        return productRepository.findById(productId)
                .filter(p -> p.getStatus() == ProductStatus.ACTIVE)
                .map(p -> new ProductSnapshot(p.getId(), p.getPrice()));
    }
}
