package com.example.zencom.zencom_shop.modules.notification.application.exceptions;

public class DuplicateDeduplicationException extends RuntimeException {
    public DuplicateDeduplicationException(String key, Throwable cause) {
        super("Duplicate deduplication key: " + key, cause);
    }
}
