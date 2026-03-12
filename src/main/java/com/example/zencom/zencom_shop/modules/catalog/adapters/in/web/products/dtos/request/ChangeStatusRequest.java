package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ChangeStatusRequest(
        @NotNull
        @JsonProperty("status")
        ProductStatus productStatus
) {
}
