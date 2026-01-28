package com.example.zencom.zencom_shop.modules.users.application.ports;

public interface PasswordHasher {
    String hashPassword(String password);

    void validatePassword(String password, String hashedPassword);

}
