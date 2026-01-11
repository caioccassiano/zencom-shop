package com.example.zencom.zencom_shop.modules.shared.domain;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AggrgateRoot {

    private final List<DomainEvent> events = new ArrayList<>();

    protected void raise(DomainEvent event) {
        events.add(event);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> copy = new ArrayList<>(events);
        events.clear();
        return Collections.unmodifiableList(copy);
    }
}
