package com.example.zencom.zencom_shop.modules.users.application.ports.in;

import com.example.zencom.zencom_shop.modules.users.application.dtos.input.LoginUserCommandDTO;
import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserAuthenticatedDTO;

public interface LoginUserUseCase {

    UserAuthenticatedDTO execute(LoginUserCommandDTO command);
}
