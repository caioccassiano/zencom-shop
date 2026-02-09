package com.example.zencom.zencom_shop.modules.users.adapters.in.controllers;

import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.requests.LoginRequestDTO;
import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.requests.RegisterRequestDTO;
import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.responses.LoginResponseDTO;
import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.mappers.UserHttpMapper;
import com.example.zencom.zencom_shop.modules.users.application.dtos.input.LoginUserCommandDTO;
import com.example.zencom.zencom_shop.modules.users.application.ports.in.LoginUserUseCase;
import com.example.zencom.zencom_shop.modules.users.application.ports.in.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUserUseCase loginUserUseCase;

    public AuthController(
            RegisterUserUseCase registerUserUseCase,
            LoginUserUseCase loginUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUserUseCase = loginUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequestDTO body ){
        var cmd = UserHttpMapper.toCommand(body);
        registerUserUseCase.execute(cmd);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO body ){
        var cmd = new LoginUserCommandDTO(body.email(), body.password());
        var result = loginUserUseCase.execute(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDTO(result.token()));
    }
}
