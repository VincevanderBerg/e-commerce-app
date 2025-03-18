package labs.codemountain.ecommerce.order.dto;

import labs.codemountain.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
    Long id,
    String reference,
    BigDecimal orderAmount,
    PaymentMethod paymentMethod,
    String customerId
) {
}
