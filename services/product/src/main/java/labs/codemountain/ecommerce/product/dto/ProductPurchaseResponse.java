package labs.codemountain.ecommerce.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductPurchaseResponse(
        Long id,
        String name,
        String description,
        Integer quantity,
        BigDecimal price
) {
}
