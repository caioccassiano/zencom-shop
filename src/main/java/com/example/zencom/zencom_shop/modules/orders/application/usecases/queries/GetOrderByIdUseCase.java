package com.example.zencom.zencom_shop.modules.orders.application.usecases.queries;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderResultDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderResultMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

public class GetOrderByIdUseCase {
    private final OrdersRepository ordersRepository;
    public GetOrderByIdUseCase(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public OrderResultDTO getOrderById(String id) {
        return
                this.ordersRepository.
                        findById(OrderId.from(id))
                        .map(OrderResultMapper::toDto)
                        .orElseThrow(()->
                                new OrderNotFoundException("Order with id " + id + " not found"));
    }
}
