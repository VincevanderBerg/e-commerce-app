package labs.codemountain.ecommerce.payment;

import labs.codemountain.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
