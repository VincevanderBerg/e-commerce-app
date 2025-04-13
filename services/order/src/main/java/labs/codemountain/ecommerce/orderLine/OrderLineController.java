package labs.codemountain.ecommerce.orderLine;

import labs.codemountain.ecommerce.orderLine.dto.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService service;

    @GetMapping("order/{id}")
    public List<OrderLineResponse> findById(@PathVariable("id") Long orderId) {
        return service.findAllByOrderId(orderId);
    }
}
