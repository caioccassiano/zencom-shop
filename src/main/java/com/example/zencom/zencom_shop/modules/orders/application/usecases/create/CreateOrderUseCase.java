package com.example.zencom.zencom_shop.modules.orders.application.usecases.create;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.CreateOrderCommand;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.CreateOrderItemDTO;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderResultDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.InvalidOrderCommandException;
import com.example.zencom.zencom_shop.modules.orders.application.exception.ProductHasNotEnoughStockException;
import com.example.zencom.zencom_shop.modules.orders.application.exception.ProductNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderIntegrationEventMapper;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderResultMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.catalog.ProductCatalogPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;
import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CreateOrderUseCase {

    private final OrdersRepository ordersRepository;
    private final InventoryPort inventoryPort;
    private final ProductCatalogPort productCatalogPort;
    private final IntegrationEventPublisher eventPublisher;
    private final OrderIntegrationEventMapper eventMapper;

    public CreateOrderUseCase(
            OrdersRepository ordersRepository,
            InventoryPort inventoryPort,
            ProductCatalogPort productCatalogPort,
            IntegrationEventPublisher eventPublisher,
            OrderIntegrationEventMapper eventMapper
    ){
        this.ordersRepository = ordersRepository;
        this.inventoryPort = inventoryPort;
        this.productCatalogPort = productCatalogPort;
        this.eventPublisher = eventPublisher;
        this.eventMapper = eventMapper;
    }

    public OrderResultDTO execute(CreateOrderCommand command){
        validateCommand(command);
        Order order = createOrder(command);

        Order saved = this.ordersRepository.save(order);

        publishEvents(order); //side effects

        return OrderResultMapper.toDto(saved);



    }

    private Order createOrder(CreateOrderCommand command) {
        List<OrderItem> items = buildOrderItems(command);
        Order order = Order.create(command.userId(), items);
        applyDiscountIfNeeded(order, command.discount());
        return order;
    }


    private List<OrderItem> buildOrderItems(CreateOrderCommand command) {
        return command.items().stream()
                .map(this::processItem)
                .toList();
    }

    private OrderItem processItem(CreateOrderItemDTO dto) {
        ProductId productId = ProductId.from(dto.productId());

        ProductCatalogPort.ProductSnapshot product = findActiveProduct(productId);
        ensureHasAvailableStock(productId, dto.quantity());
        inventoryPort.reserve(productId, dto.quantity());
        return OrderItem.create(
                product.productId(),
                product.name(),
                product.price(),
                dto.quantity()
        );

    }



    private ProductCatalogPort.ProductSnapshot findActiveProduct(ProductId productId) {
        return productCatalogPort.findActiveById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    private void ensureHasAvailableStock(ProductId productId, int quantity) {
        if(!inventoryPort.hasAvailable(productId, quantity)){
            throw new ProductHasNotEnoughStockException("Product has not enough stock");
        }
    }

    private void applyDiscountIfNeeded(Order order, BigDecimal discount) {
        if(discount == null|| discount.equals(BigDecimal.ZERO)) return;
        order.applyDiscount(discount);
    }

    private void publishEvents(Order order){
        var integrationEvents = order.pullDomainEvents()
                .stream()
                .map(eventMapper::toIntegration)
                .flatMap(Optional::stream)
                .toList();
        eventPublisher.publish(integrationEvents);
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
