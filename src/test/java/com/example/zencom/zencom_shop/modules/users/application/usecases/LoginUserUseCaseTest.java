package com.example.zencom.zencom_shop.modules.users.application.usecases;

import com.example.zencom.zencom_shop.modules.users.application.dtos.input.LoginUserCommandDTO;
import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserAuthenticatedDTO;
import com.example.zencom.zencom_shop.modules.users.application.exception.InvalidCredentials;
import com.example.zencom.zencom_shop.modules.users.application.ports.PasswordHasher;
import com.example.zencom.zencom_shop.modules.users.application.ports.TokenService;
import com.example.zencom.zencom_shop.modules.users.application.ports.UserRepository;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginUserUseCaseTest {
    private LoginUserUseCase loginUserUseCase;
    private UserRepository userRepository;
    private PasswordHasher passwordHasher;
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordHasher = mock(PasswordHasher.class);
        tokenService = mock(TokenService.class);

        loginUserUseCase = new LoginUserUseCase(
                userRepository,
                tokenService,
                passwordHasher
        );
    }
    @Test
    void should_be_able_to_login() {
        String email = "caio@gmail.com";
        String password = "123456";

        User user = mock(User.class);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        doNothing()
                .when(passwordHasher)
                        .validatePassword(anyString(), anyString());
        when(tokenService.generateToken(user)).thenReturn("token");

        UserAuthenticatedDTO result = loginUserUseCase.execute(
                new LoginUserCommandDTO(email, password)
        );
        assertNotNull(result);
        assertEquals("token", result.token());
        verify(userRepository).findByEmail(email);
        verify(passwordHasher).validatePassword(password, user.getPassword());
        verify(tokenService).generateToken(user);
    }
    @Test
    void should_not_be_able_to_login() {
        String email = "caio@gmail.com";
        String password = "123456";
        User user = mock(User.class);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(user.getPassword()).thenReturn("hashedPassword");
        doThrow(new InvalidCredentials("Invalid email or password"))
                .when(passwordHasher).validatePassword(anyString(), anyString());
        LoginUserCommandDTO command = new LoginUserCommandDTO(email, password);
        assertThrows(InvalidCredentials.class, () -> loginUserUseCase.execute(command));
        verify(userRepository).findByEmail(email);
    }

}