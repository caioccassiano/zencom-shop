package com.example.zencom.zencom_shop.modules.users.application.exception;

public class UserNotFoundExcpetion extends RuntimeException {
    public UserNotFoundExcpetion() {
        super("User not found or does not exist");
    }
}
