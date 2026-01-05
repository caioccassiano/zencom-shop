package com.example.zencom.zencom_shop.modules.orders.application.usecases.queries;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderResultDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.UserHasNoOrdersException;
import com.example.zencom.zencom_shop.modules.orders.application.exception.UserNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderResultMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;

import java.util.List;
import java.util.UUID;

public class ListOrdersByUserUseCase {
    private final OrdersRepository ordersRepository;
    public ListOrdersByUserUseCase(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<OrderResultDTO> execute(UUID userId) {
        if(userId == null) {
            throw new UserNotFoundException("User does not exist or not found");
        }
       List<Order> orders = ordersRepository.findByUserId(userId);
        if(orders.isEmpty()) {
            throw new UserHasNoOrdersException("User does not have orders");
        }
        return orders.stream()
                .map(OrderResultMapper::toDto)
                .toList();
    }
}
