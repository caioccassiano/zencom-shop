package com.example.zencom.zencom_shop.modules.catalog.application.usecases.product.admin;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.CreateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product.CreateProductUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.product.ProductRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.zencom.zencom_shop.modules.catalog.application.mappers.ProductResultMapper.toResult;

@Service
@Transactional
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final InventoryItemRepository  inventoryItemRepository;

    public CreateProductUseCaseImpl(
            ProductRepository productRepository,
            InventoryItemRepository inventoryItemRepository) {
        this.productRepository = productRepository;
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public ProductResultDTO create (CreateProductCommand dto) {
      Product product = Product.create(dto.name(), dto.description(), dto.price());
      this.productRepository.save(product);
      InventoryItem inventoryItem = InventoryItem.create(product.getId());
      inventoryItemRepository.save(inventoryItem);
      return toResult(product);

    };


}
