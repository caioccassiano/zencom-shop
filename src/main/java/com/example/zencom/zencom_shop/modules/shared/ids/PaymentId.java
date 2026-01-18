package com.example.zencom.zencom_shop.modules.shared.ids;

import java.util.UUID;

public final class PaymentId {
    private final UUID id;

    private PaymentId(UUID id) {
        this.id = id;
    }

    public static PaymentId newId() {
        return new  PaymentId(UUID.randomUUID());
    }
    public static PaymentId from(String paymentId) {
        try {
            return new PaymentId(UUID.fromString(paymentId));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(paymentId);
        }

    }

    public UUID getId() {
        return id;
    }

    public static PaymentId fromUUID(UUID uuid) {
        return new PaymentId(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId that = (PaymentId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
