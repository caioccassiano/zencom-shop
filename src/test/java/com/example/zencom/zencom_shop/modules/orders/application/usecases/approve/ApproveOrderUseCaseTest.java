package com.example.zencom.zencom_shop.modules.orders.application.usecases.approve;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.ApproveOrderDTO;
import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;
import com.example.zencom.zencom_shop.modules.orders.domain.enums.OrderStatus;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ApproveOrderUseCaseTest {
    private ApproveOrderUseCase approveOrderUseCase;
    private OrdersRepository ordersRepository;
    private InventoryPort inventoryPort;
    private IntegrationEventEmitter emitter;

    private final UUID orderId = UUID.randomUUID();
    @BeforeEach
    void setUp() {
        ordersRepository = mock(OrdersRepository.class);
        emitter = mock(IntegrationEventEmitter.class);
        inventoryPort = mock(InventoryPort.class);
        approveOrderUseCase = new ApproveOrderUseCase(ordersRepository, inventoryPort, emitter);
    }

    @Test
    void should_confirm_an_order(){

        Order order = mock(Order.class);
        OrderItem item1 = mock(OrderItem.class);
        OrderItem item2 = mock(OrderItem.class);

        ProductId product1 = mock(ProductId.class);
        ProductId product2 = mock(ProductId.class);

        when(item1.getProductId()).thenReturn(product1);
        when(item1.getQuantity()).thenReturn(2);

        when(item2.getProductId()).thenReturn(product2);
        when(item2.getQuantity()).thenReturn(1);

        when(order.getStatus()).thenReturn(OrderStatus.APPROVED);
        when(order.getOrderItems()).thenReturn(List.of(item1, item2));

        when(ordersRepository.findById(any(OrderId.class))).thenReturn(Optional.of(order));

        approveOrderUseCase.execute(new ApproveOrderDTO(orderId));

        verify(order, times(1)).confirm();
        verify(inventoryPort,times(1)).commit(product1,2);
        verify(inventoryPort,times(1)).commit(product2,1);
        verify(ordersRepository, times(1)).findById(any(OrderId.class));
        verify(ordersRepository, times(1)).save(order);
    }

}