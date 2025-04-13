package labs.codemountain.ecommerce.kafka;

import labs.codemountain.ecommerce.customer.CustomerResponse;
import labs.codemountain.ecommerce.order.PaymentMethod;
import labs.codemountain.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
