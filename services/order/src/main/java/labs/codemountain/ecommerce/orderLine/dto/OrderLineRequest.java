package labs.codemountain.ecommerce.orderLine.dto;

import lombok.Builder;

@Builder
public record OrderLineRequest(
        Long id,
        Long orderId,
        Long productId,
        Integer quantity
) {
}
