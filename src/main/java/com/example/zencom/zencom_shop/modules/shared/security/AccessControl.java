package com.example.zencom.zencom_shop.modules.shared.security;

import com.example.zencom.zencom_shop.modules.shared.security.exceptions.ForbiddenException;
import com.example.zencom.zencom_shop.modules.shared.security.exceptions.UnauthenticatedException;

public final class AccessControl {
    private AccessControl() {}

    public static void requireAuthenticated(AuthContext actor) {
        if (actor == null || actor.userId() == null) {
            throw new UnauthenticatedException("Unauthenticated");
        }
    }

    public static void requireRole(AuthContext actor, Role requiredRole) {
        requireAuthenticated(actor);

        if (!actor.has(requiredRole)) {
            throw new ForbiddenException("Missing role: " + requiredRole);
        }
    }

    public static void requireAnyRole(AuthContext actor, Role... roles) {
        requireAuthenticated(actor);

        if (!actor.hasAny(roles)) {
            throw new ForbiddenException("Missing required roles");
        }
    }
}
