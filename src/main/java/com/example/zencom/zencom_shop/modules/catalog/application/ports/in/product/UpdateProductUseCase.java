package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.UpdateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.outputs.ProductResultDTO;

public interface UpdateProductUseCase {
    ProductResultDTO update(UpdateProductCommand command);
}
