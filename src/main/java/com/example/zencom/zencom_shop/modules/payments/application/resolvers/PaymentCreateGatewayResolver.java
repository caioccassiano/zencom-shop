package com.example.zencom.zencom_shop.modules.payments.application.resolvers;

import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentCreationGateway;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentCreateGatewayResolver {
    private final Map<String, PaymentCreationGateway> gateways;

    public PaymentCreateGatewayResolver(List<PaymentCreationGateway> gatewaysList) {
        this.gateways = gatewaysList.stream()
                .collect(Collectors.toMap(PaymentCreationGateway::provider, gateway -> gateway));
    }

    public PaymentCreationGateway resolve(String provider){
        var gateway = gateways.get(provider);
        if (gateway == null) throw new IllegalStateException("No PaymentCreationGateway found for provider " + provider);
        return gateway;
    }


}
