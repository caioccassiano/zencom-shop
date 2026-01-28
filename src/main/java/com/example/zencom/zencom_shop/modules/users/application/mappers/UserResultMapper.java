package com.example.zencom.zencom_shop.modules.users.application.mappers;

import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserResultDTO;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;

public class UserResultMapper {
    private UserResultMapper(){}
    public static UserResultDTO toDTO(User user){
        return new UserResultDTO(
                user.getId().getId(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
