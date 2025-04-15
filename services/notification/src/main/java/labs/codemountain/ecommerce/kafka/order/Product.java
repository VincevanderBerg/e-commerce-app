package labs.codemountain.ecommerce.kafka.order;

import java.math.BigDecimal;

public record Product(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
