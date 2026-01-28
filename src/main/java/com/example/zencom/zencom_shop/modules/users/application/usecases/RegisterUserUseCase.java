package com.example.zencom.zencom_shop.modules.users.application.usecases;

import com.example.zencom.zencom_shop.modules.users.application.dtos.input.RegisterUserCommandDTO;
import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserResultDTO;
import com.example.zencom.zencom_shop.modules.users.application.exception.EmailAlreadyInUseException;
import com.example.zencom.zencom_shop.modules.users.application.mappers.UserResultMapper;
import com.example.zencom.zencom_shop.modules.users.application.ports.PasswordHasher;
import com.example.zencom.zencom_shop.modules.users.application.ports.UserRepository;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;
import com.example.zencom.zencom_shop.modules.users.domain.enums.NotificationChannel;

import java.util.Optional;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PasswordHasher hasher;

    public RegisterUserUseCase(UserRepository userRepository, PasswordHasher hasher) {
        this.userRepository = userRepository;
        this.hasher = hasher;
    }

    public UserResultDTO execute(RegisterUserCommandDTO command){
        validateCommand(command);
        validateEmail(command.email());
        NotificationChannel channel = Optional.ofNullable(command.channel())
                .orElse(NotificationChannel.EMAIL);
        String hashedPassword = hasher.hashPassword(command.password());
        User user = User.create(
                command.email(),
                hashedPassword,
                channel,
                command.phoneNumber()
        );
        userRepository.save(user);
        return UserResultMapper.toDTO(user);


    }

    private void validateCommand(RegisterUserCommandDTO command){
        if(command.email() == null || command.email().isEmpty()) throw new RuntimeException();
        if(command.password() == null || command.password().isEmpty()) throw new RuntimeException();
    }
    private void validateEmail(String email){
        if(this.userRepository.findByEmail(email).isPresent()) throw new EmailAlreadyInUseException();
    }
}

