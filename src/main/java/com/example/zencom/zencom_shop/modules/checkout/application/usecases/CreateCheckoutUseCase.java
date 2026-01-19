package com.example.zencom.zencom_shop.modules.checkout.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.ports.catalog.CatalogPort;
import com.example.zencom.zencom_shop.modules.checkout.application.dtos.input.CreateCheckoutCommandDTO;
import com.example.zencom.zencom_shop.modules.checkout.application.dtos.output.CheckoutResultDTO;
import com.example.zencom.zencom_shop.modules.checkout.application.mappers.Pricing;
import com.example.zencom.zencom_shop.modules.checkout.application.ports.*;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart.CartItemSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart.CartSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.catalog.ProductsSnapshots;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory.InventoryReservationSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory.ReservationItem;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory.ReservationRequest;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory.ReservationStatus;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders.CreateOrderRequest;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders.OrderCreatedSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments.CreatePaymentRequest;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments.PaymentCreatedSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments.PaymentStatus;

import java.util.UUID;

public class CreateCheckoutUseCase {
    private final CartPort cartPort;
    private final OrdersPort ordersPort;
    private final InventoryPort inventoryPort;
    private final ProductsPort productsPort;
    private final PaymentPort paymentPort;

    public CreateCheckoutUseCase(
            CartPort cartPort,
            OrdersPort ordersPort,
            InventoryPort inventoryPort,
            ProductsPort productsPort,
            PaymentPort paymentPort
    ){
        this.cartPort = cartPort;
        this.ordersPort = ordersPort;
        this.inventoryPort = inventoryPort;
        this.productsPort = productsPort;
        this.paymentPort = paymentPort;
    }

    public CheckoutResultDTO execute(CreateCheckoutCommandDTO command) {
        validateInput(command);
        CartSnapshot cart = fetchCart(command.costumerId());
        ProductsSnapshots products = fetchProducts(cart);
        Pricing pricing = buildPricing(cart, products);
        InventoryReservationSnapshot reservation = null;
        OrderCreatedSnapshot order = null;
        try{
            reservation = reserveInventory(command,cart);
            order = createOrder(
                    cart,
                    pricing,
                    reservation
            );
            PaymentCreatedSnapshot payment = createdPayment(command,cart,pricing,order);
            return buildResult(
                    pricing,
                    reservation,
                    order,
                    payment

            );
        } catch (RuntimeException ex){
            compensateReservation(reservation);

            throw ex;
        }




    }

    private void validateInput(CreateCheckoutCommandDTO command) {
        if(command==null) throw new IllegalArgumentException("command is required");
        if(command.costumerId()==null) throw new IllegalArgumentException("costumerId is required");
        if(command.method()==null) throw new IllegalArgumentException("method is required");
    }

    private CartSnapshot fetchCart(UUID costumerId){
        CartSnapshot cart = cartPort.getActiveCart(costumerId);
        if(cart==null) throw new IllegalArgumentException("cart is required");
        if (cart.items()== null || cart.items().isEmpty()) throw new IllegalArgumentException("items is required");

        boolean hasInvalidQuantity = cart.items()
                .stream()
                .anyMatch(item -> item.quantity() <=0);
        if (hasInvalidQuantity) {
            throw new IllegalArgumentException("items quantity must be greater than 0");
        }
        return cart;

    }

    private ProductsSnapshots fetchProducts(CartSnapshot cart){
        var productIds = cart.items().stream()
                .map(CartItemSnapshot::productId)
                .distinct()
                .toList();
        ProductsSnapshots products = productsPort.getProducts(productIds);
        if(products==null) throw new IllegalArgumentException("products is required");
        return products;
    }

    private Pricing buildPricing(CartSnapshot cart, ProductsSnapshots products){
        return Pricing.from(cart, products);
    }

    private InventoryReservationSnapshot reserveInventory(CreateCheckoutCommandDTO command, CartSnapshot cart){
        ReservationRequest request = new ReservationRequest(
                command.idempotencyKey(),
                command.costumerId(),
                cart.items().stream()
                        .map(cartItem -> new ReservationItem(
                                cartItem.productId(),
                                cartItem.quantity()
                        ))
                        .toList()
        );
        InventoryReservationSnapshot reservation = inventoryPort.reserve(request);
        if(reservation==null || reservation.reservationId()==null) throw new IllegalArgumentException("reservation is required");
        if(reservation.status()!=null && reservation.status() != ReservationStatus.RESERVED) throw new IllegalArgumentException("reservation status is required");

        return reservation;
    }

    private OrderCreatedSnapshot createOrder(
            CartSnapshot cart,
            Pricing pricing,
            InventoryReservationSnapshot reservation
    ){
        CreateOrderRequest request = new CreateOrderRequest(
                cart.costumerId(),
                pricing.toOrderItems(),
                reservation.reservationId(),
                pricing.totalAmount()
        );
        OrderCreatedSnapshot order = ordersPort.createOrder(request);
        if(order==null || order.orderId()==null) throw new IllegalArgumentException("order is required");
        return order;
    }

    private PaymentCreatedSnapshot createdPayment(CreateCheckoutCommandDTO command,
                                                  CartSnapshot cart,
                                                  Pricing pricing,
                                                  OrderCreatedSnapshot order){
        CreatePaymentRequest request = new CreatePaymentRequest(
                order.orderId(),
                cart.costumerId(),
                pricing.totalAmount(),
                command.method()
        );
        PaymentCreatedSnapshot payment = paymentPort.createPayment(request);
        if(payment==null || payment.paymentId()==null) throw new IllegalArgumentException("payment is required");
        if(payment.status()== PaymentStatus.FAILED||payment.status()==PaymentStatus.CANCELED) throw new IllegalArgumentException("payment status is required");
        return payment;
    }
    private CheckoutResultDTO buildResult(
            Pricing pricing,
            InventoryReservationSnapshot reservation,
            OrderCreatedSnapshot order,
            PaymentCreatedSnapshot payment
    ){
        return new CheckoutResultDTO(
                order.orderId(),
                payment.paymentId(),
                reservation.reservationId(),
                pricing.totalAmount(),
                payment.status()
        );
    }

    private void compensateReservation(InventoryReservationSnapshot reservation){
        if(reservation==null || reservation.reservationId()==null) return;
        try {
            inventoryPort.release(reservation.reservationId());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
