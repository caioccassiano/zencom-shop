package com.example.zencom.zencom_shop.modules.users.adapters.out;

import com.example.zencom.zencom_shop.modules.shared.ids.UserId;
import com.example.zencom.zencom_shop.modules.users.application.ports.out.UserRepository;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryAdapter implements UserRepository {
    private final UserJpaRepository jpaRepository;

    public UserRepositoryAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(User user) {
        UserJpaEntity entity = UserPersistenceMapper.toEntity(user);
        UserJpaEntity savedEntity = jpaRepository.save(entity);
    }

    @Override
    public Optional<User> findByEmail(String email){
        return jpaRepository.findByEmail(email)
                .map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.getId())
                .map(UserPersistenceMapper::toDomain);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserPersistenceMapper::toDomain)
                .toList();
    }
    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

}
