package com.example.zencom.zencom_shop.modules.catalog.domain.entities;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductUpdatedDomainEvent;
import com.example.zencom.zencom_shop.modules.catalog.domain.exceptions.InvalidPriceException;
import com.example.zencom.zencom_shop.modules.shared.domain.AggrgateRoot;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class Product extends AggrgateRoot {

    ProductId id;
    String name;
    String description;
    BigDecimal price;
    ProductStatus status;
    Instant createdAt;
    Instant updatedAt;

    public Product(
            ProductId id,
            String name,
            String description,
            BigDecimal price,
            ProductStatus status,
            Instant createdAt,
            Instant updatedAt
            ) {
        this.id = Objects.requireNonNull(id);
        setName(name);
        setDescription(description);
        setPrice(price);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.updatedAt = Objects.requireNonNull(updatedAt);
    }

    public static Product create(
            String name,
            String description,
            BigDecimal price){
        validatePrice(price);
        var now = Instant.now();
        ProductId productId = ProductId.newId();

        Product product = new Product(
                productId,
                name,
                description,
                price,
                ProductStatus.ACTIVE,
                now,
                now
        );
        product.raise(ProductCreatedDomainEvent.now(
                productId.getId()
        ));
        return product;
    }
    public void update(String name, String description, BigDecimal price) {
        if (name != null) setName(name);
        if (description != null) setDescription(description);
        if (price != null) setPrice(price);
        raise(ProductUpdatedDomainEvent.now(
                id.getId()
        ));
        touch();
    }

    public void changeStatus(ProductStatus newStatus) {
        if(status == null) throw  new IllegalStateException("Status is null");
        this.status = newStatus;
        touch();
    }

    private static void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceException();
        }
    }
    private void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        this.name = name.trim();
    }

    private void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    private void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    public void ensureIsActive() {
        if(this.status != ProductStatus.ACTIVE)
            throw new IllegalStateException("Product is active");
    }

    public ProductId getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }
    public ProductStatus getStatus() {
        return status;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }


}
