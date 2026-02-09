package com.example.zencom.zencom_shop.modules.users.application.ports.in;

import com.example.zencom.zencom_shop.modules.users.application.dtos.input.RegisterUserCommandDTO;
import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserResultDTO;

public interface RegisterUserUseCase {

    UserResultDTO execute(RegisterUserCommandDTO command);
}
