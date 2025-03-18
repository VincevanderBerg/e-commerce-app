package labs.codemountain.ecommerce.orderLine;

import labs.codemountain.ecommerce.order.Order;
import labs.codemountain.ecommerce.orderLine.dto.OrderLineRequest;
import labs.codemountain.ecommerce.orderLine.dto.OrderLineResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {

    public final OrderLine toEntity(OrderLineRequest request) {
        return OrderLine.builder()
                .id(request.id())
                .order(Order.builder().id(request.orderId()).build())
                .productId(request.productId())
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toResponse(OrderLine entity) {
        return OrderLineResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .productId(entity.getProductId())
                .quantity(entity.getQuantity())
                .build();
    }
}
