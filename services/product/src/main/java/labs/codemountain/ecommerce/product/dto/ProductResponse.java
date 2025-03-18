package labs.codemountain.ecommerce.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        Long id,
        String name,
        String description,
        Integer quantity,
        BigDecimal price,
        Long categoryId,
        String categoryName,
        String categoryDescription
) {
}
