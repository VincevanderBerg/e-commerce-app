package labs.codemountain.ecommerce.order.dto;

import labs.codemountain.ecommerce.order.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderResponse(
    Long id,
    String reference,
    BigDecimal orderAmount,
    PaymentMethod paymentMethod,
    String customerId
) {
}
