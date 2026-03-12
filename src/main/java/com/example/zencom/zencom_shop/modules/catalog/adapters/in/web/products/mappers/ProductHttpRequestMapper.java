package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.mappers;

import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request.ChangeStatusRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request.CreateProductRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request.UpdateProductRequest;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.ChangeProductStatusCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.CreateProductCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.product.inputs.UpdateProductCommand;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.UUID;

public final class ProductHttpRequestMapper {
    private ProductHttpRequestMapper() {}

    public static CreateProductCommand toCreateCommand(CreateProductRequest request) {
        return new CreateProductCommand(
                request.name(),
                request.description(),
                request.price()
        );
    }

    public static UpdateProductCommand toUpdateCommand(UUID id, UpdateProductRequest request) {
        return new UpdateProductCommand(
                ProductId.from_UUID(id),
                request.name(),
                request.description(),
                request.price()

        );
    }

    public static ChangeProductStatusCommand toChangeStatusCommand(UUID id, ChangeStatusRequest request) {
        return new ChangeProductStatusCommand(
                ProductId.from_UUID(id),
                request.productStatus()
        );
    }
}
