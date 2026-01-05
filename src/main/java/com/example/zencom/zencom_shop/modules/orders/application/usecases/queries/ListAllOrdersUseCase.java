package com.example.zencom.zencom_shop.modules.orders.application.usecases.queries;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderResultDTO;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderResultMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;

import java.util.List;

public class ListAllOrdersUseCase {
    private final OrdersRepository ordersRepository;
    public ListAllOrdersUseCase(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    //Soon implementing pagination
    //ADMIN only will be implemented as well
    List<OrderResultDTO> execute() {
        return this.ordersRepository
                .findAll()
                .stream()
                .map(OrderResultMapper::toDto)
                .toList();
    }
}
