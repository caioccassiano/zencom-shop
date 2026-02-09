package com.example.zencom.zencom_shop.modules.users.adapters.in.controllers;

import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.responses.UserDetailResponseDTO;
import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.mappers.UserHttpResponseMapper;
import com.example.zencom.zencom_shop.modules.users.application.ports.in.GetUserDetailsUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final GetUserDetailsUseCase getUserDetailsUseCase;

    public UserController(GetUserDetailsUseCase getUserDetailsUseCase) {
        this.getUserDetailsUseCase = getUserDetailsUseCase;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDetailResponseDTO> me(Authentication authentication) {
        UUID userId = UUID.fromString(authentication.getName());
        var result =  getUserDetailsUseCase.execute(userId);
        return ResponseEntity.ok(UserHttpResponseMapper.toController(result));
    }


}
