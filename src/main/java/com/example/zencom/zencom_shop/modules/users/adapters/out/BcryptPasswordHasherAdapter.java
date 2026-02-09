package com.example.zencom.zencom_shop.modules.users.adapters.out;

import com.example.zencom.zencom_shop.modules.users.application.exception.InvalidCredentials;
import com.example.zencom.zencom_shop.modules.users.application.ports.out.PasswordHasher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHasherAdapter implements PasswordHasher {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hashPassword(String password) {
        return encoder.encode(password);
    }

    @Override
    public void validatePassword(String password, String hashedPassword) {
        if (!encoder.matches(password, hashedPassword)) {
            throw new InvalidCredentials("Invalid password");
        }
    }

}
