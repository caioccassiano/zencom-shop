package com.example.zencom.zencom_shop.modules.users.adapters.out;

import com.example.zencom.zencom_shop.modules.shared.ids.UserId;
import com.example.zencom.zencom_shop.modules.shared.security.Role;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;
import com.example.zencom.zencom_shop.modules.users.domain.enums.NotificationChannel;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class UserPersistenceMapper {

    private UserPersistenceMapper() {}

    public static UserJpaEntity toEntity(User user){
        UserJpaEntity userJpaEntity = new UserJpaEntity();
        userJpaEntity.setId(user.getId().getId());
        userJpaEntity.setEmail(user.getEmail());
        userJpaEntity.setPasswordHash(user.getPassword());
        userJpaEntity.setRole(rolesToString(user.getRole()));
        userJpaEntity.setNotificationChannel(user.getChannel().name());
        userJpaEntity.setPhoneNumber(user.getPhoneNumber());
        userJpaEntity.setCreatedAt(user.getCreatedAt());
        userJpaEntity.setUpdatedAt(user.getUpdatedAt());

        return userJpaEntity;
    }

    public static User toDomain(UserJpaEntity userJpaEntity){
        Set<Role> roles = stringToRoles(userJpaEntity.getRole());

        return User.restore(
                UserId.fromUUID(userJpaEntity.getId()),
                userJpaEntity.getEmail(),
                userJpaEntity.getPasswordHash(),
                roles,
                NotificationChannel.valueOf(userJpaEntity.getNotificationChannel()),
                userJpaEntity.getPhoneNumber(),
                userJpaEntity.getCreatedAt(),
                userJpaEntity.getUpdatedAt()
        );
    }


    /*---HELPERS(roles)---*/

    private static String rolesToString(Set<Role> roles){
        if (roles == null || roles.isEmpty()){
            return Role.CLIENT.name();
        }
        return  roles.stream()
                .map((Role::name))
                .sorted()
                .collect(Collectors.joining(","));
    }

    private static Set<Role> stringToRoles(String roles){
        if (roles == null || roles.isEmpty()){
            return Set.of(Role.CLIENT);
        }
        return Arrays.stream(roles.split(","))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
