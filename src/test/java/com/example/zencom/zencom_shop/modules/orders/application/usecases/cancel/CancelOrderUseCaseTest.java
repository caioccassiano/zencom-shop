package com.example.zencom.zencom_shop.modules.orders.application.usecases.cancel;

import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CancelOrderUseCaseTest {

    private CancelOrderUseCase cancelOrderUseCase;
    private OrdersRepository ordersRepository;
    private IntegrationEventEmitter emitter;

    private final UUID orderId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        ordersRepository = mock(OrdersRepository.class);
        cancelOrderUseCase = new CancelOrderUseCase(ordersRepository, emitter);
    }

    @Test
    void should_cancel_order() {
        Order order = mock(Order.class);

        when(ordersRepository.findById(any(OrderId.class))).thenReturn(Optional.of(order));

        cancelOrderUseCase.execute(orderId);

        verify(ordersRepository, times(1)).findById(any(OrderId.class));
        verify(order, times(1)).cancel();
        verify(ordersRepository, times(1)).save(order);

    }

}