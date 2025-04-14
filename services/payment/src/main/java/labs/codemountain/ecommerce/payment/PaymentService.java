package labs.codemountain.ecommerce.payment;

import labs.codemountain.ecommerce.notification.NotificationProducer;
import labs.codemountain.ecommerce.notification.PaymentNotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public final PaymentResponse createPayment(PaymentRequest request) {
        final Payment payment = repository.save(mapper.toEntity(request));

        notificationProducer.sendPaymentNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );

        return mapper.toResponse(payment);
    }
}
