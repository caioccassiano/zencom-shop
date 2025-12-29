package com.example.zencom.zencom_shop.modules.catalog.application.ports;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    public void save (Product product);

    public Optional<Product> findById (ProductId id);

    public Optional<Product> findByName (String name);

    public List<Product> findAll ();

    public List<Product> findAllActive ();

}
