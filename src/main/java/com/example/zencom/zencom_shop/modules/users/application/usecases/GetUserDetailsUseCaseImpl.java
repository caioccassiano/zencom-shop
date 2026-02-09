package com.example.zencom.zencom_shop.modules.users.application.usecases;

import com.example.zencom.zencom_shop.modules.shared.ids.UserId;
import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserResultDTO;
import com.example.zencom.zencom_shop.modules.users.application.exception.UserNotFoundExcpetion;
import com.example.zencom.zencom_shop.modules.users.application.ports.in.GetUserDetailsUseCase;
import com.example.zencom.zencom_shop.modules.users.application.ports.out.UserRepository;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {

    private final UserRepository userRepository;
    public GetUserDetailsUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResultDTO execute(UUID userId) {
        if(userId == null) throw new IllegalArgumentException("userId cannot be null");
        UserId id = UserId.fromUUID(userId);
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundExcpetion::new);
        return new UserResultDTO(
                user.getId().getId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
