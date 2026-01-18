package com.example.zencom.zencom_shop.modules.payments.application.handlers;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.CancelPaymentCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.MarkAsFailedCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.MarkAsPaidCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.ProviderPaymentWebhook;
import com.example.zencom.zencom_shop.modules.payments.application.usecases.CancelPaymentUseCase;
import com.example.zencom.zencom_shop.modules.payments.application.usecases.MarkPaymentAsFailedUseCase;
import com.example.zencom.zencom_shop.modules.payments.application.usecases.MarkPaymentAsPaidUseCase;

import static com.example.zencom.zencom_shop.modules.payments.application.dtos.input.WebhookPaymentStatus.PAID;
import static javax.management.remote.JMXConnectionNotification.FAILED;

public class PaymentWebhookHandler {

    private final MarkPaymentAsFailedUseCase markFailed;
    private final MarkPaymentAsPaidUseCase markPaid;
    private final CancelPaymentUseCase cancelPayment;

    public PaymentWebhookHandler(MarkPaymentAsFailedUseCase markFailed,
                                 MarkPaymentAsPaidUseCase markPaid,
                                 CancelPaymentUseCase cancelPayment) {
        this.markFailed = markFailed;
        this.markPaid = markPaid;
        this.cancelPayment = cancelPayment;
    }

    public void handle(ProviderPaymentWebhook webhook){
        if(webhook == null) throw new IllegalArgumentException("webhook cannot be null");
        if(webhook.providerReferenceId() == null || webhook.providerReferenceId().isBlank()){
            throw new IllegalArgumentException("webhook.providerReferenceId cannot be null");

        }
        if(webhook.status() == null) throw new IllegalArgumentException("webhook.status cannot be null");


        switch (webhook.status()){
            case PAID -> markPaid.execute(new MarkAsPaidCommandDTO(
                    webhook.providerReferenceId(),
                    webhook.occurredAt()
            ));
            case FAILED ->
                    markFailed.execute( new MarkAsFailedCommandDTO(
                    webhook.providerReferenceId(),
                            webhook.failureReason(),
                            webhook.occurredAt()
                    ));
            case CANCELED -> cancelPayment.execute(new CancelPaymentCommandDTO(
                    webhook.providerReferenceId(),
                    webhook.failureReason(),
                    webhook.occurredAt()
            ));
        }
    }
}
