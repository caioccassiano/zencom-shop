package com.example.zencom.zencom_shop.modules.users.application.ports;

import com.example.zencom.zencom_shop.modules.users.domain.entities.User;

public interface TokenService {
    String generateToken(User user);
}
