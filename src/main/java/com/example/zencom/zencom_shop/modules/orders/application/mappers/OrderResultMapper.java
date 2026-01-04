package com.example.zencom.zencom_shop.modules.orders.application.mappers;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderItemResultDTO;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderResultDTO;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;

import java.util.List;

public final class OrderResultMapper {
    private OrderResultMapper() {}

    public static OrderResultDTO toDto(Order order) {
        List<OrderItemResultDTO> items = order.getOrderItems()
                .stream()
                .map(OrderResultMapper::toItemDTO)
                .toList();
        return new OrderResultDTO(
                order.getOrderId().asString(),
                order.getUserId(),
                order.getStatus().name(),
                order.getSubtotal(),
                order.getDiscountTotal(),
                order.getTotal(),
                items,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    private static OrderItemResultDTO toItemDTO(OrderItem item) {
        return  new OrderItemResultDTO(
                item.getProductId().asString(),
                item.getProductName(),
                item.getUnitPrice(),
                item.getQuantity(),
                item.subtotal()
        );
    }
}
