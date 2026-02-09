package com.example.zencom.zencom_shop.modules.users.application.ports.out;

import com.example.zencom.zencom_shop.modules.shared.ids.UserId;
import com.example.zencom.zencom_shop.modules.shared.security.Role;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;

import java.util.Set;

public interface TokenService {
    String generateToken(User user);

    UserId extractUserId(String token);

    Set<Role> extractRoles(String token);

    void validate(String token);
}
