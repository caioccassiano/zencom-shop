package com.example.zencom.zencom_shop.modules.catalog.adapters.out.product;

import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;
import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryAdapter  implements ProductRepository {

    private final ProductJpaRepository jpaRepository;
    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Product product) {
        ProductJpaEntity productJpaEntity = ProductPersistenceMapper.toEntity(product);
        jpaRepository.save(productJpaEntity);

    }

    @Override
    public Optional<Product> findById(ProductId id) {
       return jpaRepository.findById(id.getId())
               .map(ProductPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(ProductPersistenceMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(ProductPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Product> findAllActive() {
        return jpaRepository.findByStatus(ProductStatus.ACTIVE).stream()
                .map(ProductPersistenceMapper::toDomain)
                .toList();
    }
}
