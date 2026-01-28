package com.example.zencom.zencom_shop.modules.shared.security;

import java.util.Set;
import java.util.UUID;

public record AuthContext(
        UUID userId,
        Set<Role> roles
) {
    public boolean has(Role role) {
        return roles != null && roles.contains(role);
    }

    public boolean hasAny(Role... requiredRoles) {
        if (roles == null) return false;
        for (Role role : requiredRoles){
            if(roles.contains(role)) return true;
        }
        return false;
    }
}
