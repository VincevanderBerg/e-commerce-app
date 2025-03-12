package labs.codemountain.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "Product name is required.")
        String name,
        @NotNull(message = "Product description is required.")
        String description,
        @Positive(message = "Product quantity must be a positive value.")
        @NotNull(message = "Product quantity is required.")
        Integer quantity,
        @Positive(message = "Product price must be a positive value.")
        @NotNull(message = "Product price is required.")
        BigDecimal price,
        @NotNull(message = "Product category is required.")
        Long categoryId
) {
}
