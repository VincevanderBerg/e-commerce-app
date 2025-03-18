package labs.codemountain.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product ID is required.")
        Long productId,
        @Positive(message = "Quantity is required.")
        Integer quantity
) {
}
