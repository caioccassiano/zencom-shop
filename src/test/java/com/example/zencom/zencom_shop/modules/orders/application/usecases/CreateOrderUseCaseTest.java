package com.example.zencom.zencom_shop.modules.orders.application.usecases;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.CreateOrderCommand;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.CreateOrderItemDTO;
import com.example.zencom.zencom_shop.modules.orders.application.dtos.output.OrderResultDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.InvalidOrderCommandException;
import com.example.zencom.zencom_shop.modules.orders.application.exception.ProductHasNotEnoughStockException;
import com.example.zencom.zencom_shop.modules.orders.application.exception.ProductNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.ports.catalog.ProductCatalogPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.application.usecases.create.CreateOrderUseCase;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderUseCaseTest {
    private CreateOrderUseCase usecase;
    private InventoryPort inventoryPort;
    private ProductCatalogPort productCatalogPort;
    private OrdersRepository ordersRepository;

    private final UUID userId = UUID.randomUUID();
    private final ProductId productId1 = ProductId.newId();
    private final ProductId productId2 = ProductId.newId();

    @BeforeEach
    void setUp() {
        inventoryPort = mock(InventoryPort.class);
        productCatalogPort = mock(ProductCatalogPort.class);
        ordersRepository = mock(OrdersRepository.class);
        usecase = new CreateOrderUseCase(
                ordersRepository,
                inventoryPort,
                productCatalogPort
        );



    }

    @Test
    void should_create_order_when_valid() {
        CreateOrderCommand command = new CreateOrderCommand(
                userId,
                List.of(
                        new CreateOrderItemDTO(productId1.asString(), 2),
                        new CreateOrderItemDTO(productId2.asString(),1)
                ),
                null
        );


        //catalog mock
        when(productCatalogPort.findActiveById(productId1)).thenReturn(Optional.of(
                  new ProductCatalogPort.ProductSnapshot(
                          productId1,
                          "Product A",
                          new BigDecimal("10.00")
                  )
        ));
        when(productCatalogPort.findActiveById(productId2)).thenReturn(Optional.of(
                new ProductCatalogPort.ProductSnapshot(
                        productId2,
                          "Product B",
                          new BigDecimal("20.00")
                )
        ));

        when(inventoryPort.hasAvailable(productId1,2)).thenReturn(true);
        when(inventoryPort.hasAvailable(productId2,1)).thenReturn(true);

        doNothing().when(inventoryPort).reserve(productId1, 2);
        doNothing().when(inventoryPort).reserve(productId2, 1);

        when(ordersRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResultDTO result = usecase.execute(command);

        assertNotNull(result.orderId());
        assertEquals(userId, result.userId());
        assertEquals("PENDING", result.status());
        assertEquals(new BigDecimal("40.00"), result.subtotal());
        assertEquals(new BigDecimal("0"),result.discountTotal());
        assertEquals(new BigDecimal("40.00"), result.total());
        assertEquals(2, result.items().size());

        verify(productCatalogPort, times(1)).findActiveById(productId1);
        verify(productCatalogPort, times(1)).findActiveById(productId2);
        verify(inventoryPort, times(1)).reserve(productId1, 2);
        verify(inventoryPort, times(1)).reserve(productId2, 1);
        verify(ordersRepository, times(1)).save(any(Order.class));

    }

    @Test
    void should_throw_exception_when_product_does_not_exist_or_inactive() {
        CreateOrderCommand command = new CreateOrderCommand(
                userId,
                List.of(
                        new CreateOrderItemDTO(productId1.asString(), 4),
                        new CreateOrderItemDTO(productId2.asString(),1)
                ),
                null
        );


        when(productCatalogPort.findActiveById(productId1)).thenReturn(Optional.empty());
        when(productCatalogPort.findActiveById(productId2)).thenReturn(Optional.of(
                new ProductCatalogPort.ProductSnapshot(
                        productId2,
                        "Product B",
                        new BigDecimal("9.50")

                )
        ));

        assertThrows(ProductNotFoundException.class, () -> usecase.execute(command));
        verifyNoInteractions(inventoryPort);
        verifyNoInteractions(ordersRepository);
    }

    @Test
    void should_throw_exception_when_list_of_products_is_empty() {
        CreateOrderCommand command = new CreateOrderCommand(
                userId,
                List.of(),
                null
        );
        assertThrows(InvalidOrderCommandException.class, () -> usecase.execute(command));
    }

    @Test
    void should_throw_when_stock_is_not_enough(){
        CreateOrderCommand command = new CreateOrderCommand(
                userId,
                List.of(
                        new CreateOrderItemDTO(productId1.asString(), 2)
                        ),

                null
        );
        when(productCatalogPort.findActiveById(productId1)).thenReturn(Optional.of(
                new ProductCatalogPort.ProductSnapshot(
                        productId1,
                        "Product A",
                        new BigDecimal("10.00")
                )
        ));

        when(inventoryPort.hasAvailable(productId1,2)).thenReturn(false);

        assertThrows(ProductHasNotEnoughStockException.class, () -> usecase.execute(command));
        verifyNoInteractions(ordersRepository);

    }
}