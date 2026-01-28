package com.example.zencom.zencom_shop.modules.shared.ids;

import java.util.UUID;

public final class UserId {
    private final UUID id;

    public UserId(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        this.id = id;

    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
    public static UserId fromUUID(UUID uuid) {
        if(uuid == null) {
            throw new IllegalArgumentException("uuid cannot be null");
        }
        return new UserId(uuid);
    }
    public static UUID fromString(String raw) {
       if(raw == null) {
           throw new IllegalArgumentException("raw cannot be null");
       }
       return UUID.fromString(raw);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String asString() {
        return id.toString();
    }

    public UUID getId() {
        return id;
    }

}
