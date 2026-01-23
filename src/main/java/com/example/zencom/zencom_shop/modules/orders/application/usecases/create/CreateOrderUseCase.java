package com.example.zencom.zencom_shop.modules.orders.application.usecases.create;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.CreateOrderCommand;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.CreateOrderItemDTO;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderResultDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.InvalidOrderCommandException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderResultMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
public class CreateOrderUseCase {

    private final OrdersRepository ordersRepository;
    private final IntegrationEventEmitter integrationEventEmitter;

    public CreateOrderUseCase(
            OrdersRepository ordersRepository,
            IntegrationEventEmitter integrationEventEmitter
    ){
        this.ordersRepository = ordersRepository;
        this.integrationEventEmitter = integrationEventEmitter;
    }

    public OrderResultDTO execute(CreateOrderCommand command){
        validateCommand(command);
        Order order = createOrder(command);

        Order saved = this.ordersRepository.save(order);

        integrationEventEmitter.emitFrom(order);//side effects

        return OrderResultMapper.toDto(saved);



    }

    private Order createOrder(CreateOrderCommand command) {
        if (command == null) throw new IllegalStateException("command is required");

        return Order.create(
                command.userId(),
                command.items().stream()
                        .map(this::toOrderItem).toList(),
                command.reservationId());

    }

    private OrderItem toOrderItem(CreateOrderItemDTO dto) {
        if(dto==null)throw new IllegalArgumentException("dto is required");
        if(dto.productId()==null)throw new IllegalArgumentException("productId is required");

        ProductId productId = ProductId.from_UUID(dto.productId());
        int quantity = dto.quantity();
        BigDecimal unitPrice = dto.unitPrice();
        String productName = dto.productName();


        if(quantity<=0)throw new IllegalArgumentException("quantity must be greater than 0");
        if(unitPrice==null)throw new IllegalArgumentException("unitPrice is required");
        if(unitPrice.compareTo(BigDecimal.ZERO)<=0)throw new IllegalArgumentException("unitPrice must be greater than zero");

        return OrderItem.create(productId,productName,unitPrice,quantity);
    }


    private void validateCommand(CreateOrderCommand command){
        if (command == null) {
            throw new InvalidOrderCommandException("command cannot be null");
        }
        if (command.userId() == null) {
            throw new InvalidOrderCommandException("userId cannot be null");
        }
        if (command.items() == null || command.items().isEmpty()) {
            throw new InvalidOrderCommandException("items cannot be empty");
        }

        if (command.reservationId() == null) {
            throw new InvalidOrderCommandException("reservationId cannot be null");
        }

        for (CreateOrderItemDTO item : command.items()) {
            if (item == null) throw new InvalidOrderCommandException("item cannot be null");
            if (item.productId() == null) {
                throw new InvalidOrderCommandException("productId cannot be blank");
            }
            if (item.quantity() <= 0) {
                throw new InvalidOrderCommandException("quantity must be > 0");
            }
        }
    }

}
