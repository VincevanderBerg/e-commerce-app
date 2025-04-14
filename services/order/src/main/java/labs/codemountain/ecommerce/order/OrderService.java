package labs.codemountain.ecommerce.order;

import jakarta.persistence.EntityNotFoundException;
import labs.codemountain.ecommerce.customer.CustomerClient;
import labs.codemountain.ecommerce.customer.CustomerResponse;
import labs.codemountain.ecommerce.kafka.OrderConfirmation;
import labs.codemountain.ecommerce.kafka.OrderProducer;
import labs.codemountain.ecommerce.order.dto.OrderRequest;
import labs.codemountain.ecommerce.order.dto.OrderResponse;
import labs.codemountain.ecommerce.orderLine.OrderLineService;
import labs.codemountain.ecommerce.orderLine.dto.OrderLineRequest;
import labs.codemountain.ecommerce.payment.PaymentClient;
import labs.codemountain.ecommerce.payment.PaymentRequest;
import labs.codemountain.ecommerce.product.ProductClient;
import labs.codemountain.ecommerce.product.PurchaseRequest;
import labs.codemountain.ecommerce.product.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderLineService orderLineService;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper orderMapper;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public OrderResponse createOrder(OrderRequest request) {
        // check if customer exists
        final CustomerResponse customer = customerClient
                .getCustomerById(request.customerId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Could not create order:: Customer with id " + request.customerId() + " does not exist")
                );

        // purchase products --> product microservice
        final List<PurchaseResponse> purchasedProducts = this.productClient.purchaseProducts(request.products());

        // save order
        final Order order = repository.save(orderMapper.toEntity(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.createOrderLine(OrderLineRequest.builder()
                    .orderId(order.getId())
                    .productId(purchaseRequest.productId())
                    .quantity(purchaseRequest.quantity())
                    .build());
        }

        // initiate payment process
        final PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(request.orderAmount())
                .paymentMethod(request.paymentMethod())
                .orderId(order.getId())
                .orderReference(order.getReference())
                .customer(customer)
                .build();

        paymentClient.requestOrderPayment(paymentRequest);

        // send order confirmation --> notification microservice (kafka)
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.orderAmount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> findAllOrders() {
        return repository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderById(Long orderId) {
        return repository.findById(orderId)
                .map(orderMapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Order with id " + orderId + " does not exist")
        );
    }
}
