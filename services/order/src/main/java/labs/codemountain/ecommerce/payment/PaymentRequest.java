package labs.codemountain.ecommerce.payment;

import labs.codemountain.ecommerce.customer.CustomerResponse;
import labs.codemountain.ecommerce.order.PaymentMethod;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderReference,
        CustomerResponse customer
) {
}
