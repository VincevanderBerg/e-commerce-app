package labs.codemountain.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product ID is required.")
        Long productId,
        @NotNull(message = "Product quantity is required.")
        @Positive(message = "Product quantity must be a positive value.")
        Integer quantity
) {
}
