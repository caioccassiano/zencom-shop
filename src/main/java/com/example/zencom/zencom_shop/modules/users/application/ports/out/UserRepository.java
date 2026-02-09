package com.example.zencom.zencom_shop.modules.users.application.ports.out;

import com.example.zencom.zencom_shop.modules.shared.ids.UserId;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User>findById(UserId id);
    List<User> findAll();
    boolean existsByEmail(String email);

}
