package labs.codemountain.ecommerce.order;

import jakarta.persistence.EntityNotFoundException;
import labs.codemountain.ecommerce.customer.CustomerClient;
import labs.codemountain.ecommerce.customer.CustomerResponse;
import labs.codemountain.ecommerce.order.dto.OrderRequest;
import labs.codemountain.ecommerce.order.dto.OrderResponse;
import labs.codemountain.ecommerce.orderLine.OrderLineService;
import labs.codemountain.ecommerce.orderLine.dto.OrderLineRequest;
import labs.codemountain.ecommerce.product.ProductClient;
import labs.codemountain.ecommerce.product.PurchaseRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderLineService orderLineService;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;

    public OrderResponse createOrder(OrderRequest request) {
        // check if customer exists
        final CustomerResponse customer = customerClient
                .getCustomerById(request.customerId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Could not create order:: Customer with id " + request.customerId() + " does not exist")
                );

        // purchase products --> product microservice
        this.productClient.purchaseProducts(request.products());

        // save order
        final Order order = repository.save(orderMapper.toEntity(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.createOrderLine(OrderLineRequest.builder()
                    .orderId(order.getId())
                    .productId(purchaseRequest.productId())
                    .quantity(purchaseRequest.quantity())
                    .build());
        }

        // save order lines

        // initiate payment process

        // send order confirmation --> notification microservice (kafka)
        return null;
    }
}
