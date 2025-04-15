package labs.codemountain.ecommerce.kafka;

import jakarta.mail.MessagingException;
import labs.codemountain.ecommerce.email.EmailService;
import labs.codemountain.ecommerce.kafka.order.OrderConfirmation;
import labs.codemountain.ecommerce.kafka.payment.PaymentConfirmation;
import labs.codemountain.ecommerce.notification.Notification;
import labs.codemountain.ecommerce.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static labs.codemountain.ecommerce.notification.NotificationType.PAYMENT_CONFIRMATION;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Consuming message from payment-topic: {}", paymentConfirmation);

        repository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build()
        );

        String customerName = paymentConfirmation.customerFirstName() + " " + paymentConfirmation.customerLastName();
        emailService.sendPaymentNotificationEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Consuming message from order-topic: {}", orderConfirmation);

        repository.save(
                Notification.builder()
                        .type(PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        String customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}
