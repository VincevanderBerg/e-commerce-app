package labs.codemountain.ecommerce.order;

import jakarta.validation.Valid;
import labs.codemountain.ecommerce.order.dto.OrderRequest;
import labs.codemountain.ecommerce.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping
    @ResponseStatus(value = CREATED)
    public OrderResponse createOrder(@RequestBody @Valid OrderRequest request) {
        return service.createOrder(request);
    }

}
