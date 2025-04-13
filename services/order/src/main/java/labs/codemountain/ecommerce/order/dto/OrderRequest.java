package labs.codemountain.ecommerce.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import labs.codemountain.ecommerce.order.PaymentMethod;
import labs.codemountain.ecommerce.product.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Long id,
        String reference,
        @Positive(message = "Order amount must be a positive value.")
        BigDecimal orderAmount,
        @NotNull(message = "Payment method is required.")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer ID is required.")
        @NotEmpty(message = "Customer ID is required.")
        @NotBlank(message = "Customer ID is required.")
        String customerId,
        @NotEmpty(message = "At least one product is required to create an order.")
        List<PurchaseRequest> products
) {
}
