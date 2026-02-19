package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.product;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.ChangeProductStatusCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.CreateProductCommand;

public interface ChangeProductStatusUseCase {
    void execute(ChangeProductStatusCommand command);
}
