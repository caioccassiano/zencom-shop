package com.example.zencom.zencom_shop.modules.catalog.adapters.out.product;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, UUID> {
    List<ProductJpaEntity> findByStatus(ProductStatus status);
    Optional<ProductJpaEntity> findByName(String name);

}
