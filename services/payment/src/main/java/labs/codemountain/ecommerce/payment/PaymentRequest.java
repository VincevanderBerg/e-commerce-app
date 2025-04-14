package labs.codemountain.ecommerce.payment;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderReference,
        Customer customer
) {
}
