package labs.codemountain.ecommerce.payment;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record PaymentResponse(
        Long id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
}
