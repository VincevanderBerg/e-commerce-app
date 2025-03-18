package labs.codemountain.ecommerce.order;

import labs.codemountain.ecommerce.order.dto.OrderRequest;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public final Order toEntity(OrderRequest request) {
        return Order.builder()
                .id(request.id())
                .reference(request.reference())
                .orderAmount(request.orderAmount())
                .paymentMethod(request.paymentMethod())
                .customerId(request.customerId())
                .build();
    }
}
