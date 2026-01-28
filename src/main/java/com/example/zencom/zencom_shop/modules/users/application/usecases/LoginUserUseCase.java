package com.example.zencom.zencom_shop.modules.users.application.usecases;

import com.example.zencom.zencom_shop.modules.users.application.dtos.input.LoginUserCommandDTO;
import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserAuthenticatedDTO;
import com.example.zencom.zencom_shop.modules.users.application.exception.InvalidCredentials;
import com.example.zencom.zencom_shop.modules.users.application.ports.PasswordHasher;
import com.example.zencom.zencom_shop.modules.users.application.ports.TokenService;
import com.example.zencom.zencom_shop.modules.users.application.ports.UserRepository;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;

public class LoginUserUseCase {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PasswordHasher hasher;

    public LoginUserUseCase(UserRepository userRepository, TokenService tokenService, PasswordHasher hasher) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.hasher = hasher;
    }

    public UserAuthenticatedDTO execute(LoginUserCommandDTO command){
        validateInput(command);
        User user = this.userRepository.findByEmail(command.email())
                .orElseThrow(()-> new InvalidCredentials("User not found"));
        hasher.validatePassword(command.password(),  user.getPassword());
        String token = tokenService.generateToken(user);
        return new UserAuthenticatedDTO(
                token
        );


    }

    private void validateInput(LoginUserCommandDTO command){
        if(command.email() == null || command.email().isEmpty()) throw new RuntimeException();
        if(command.password() == null || command.password().isEmpty()) throw new RuntimeException();
    }
}
