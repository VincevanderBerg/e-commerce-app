package labs.codemountain.ecommerce.orderLine;

import labs.codemountain.ecommerce.orderLine.dto.OrderLineRequest;
import labs.codemountain.ecommerce.orderLine.dto.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public final OrderLineResponse createOrderLine(OrderLineRequest request) {
        final OrderLine result = repository.save(mapper.toEntity(request));

        return mapper.toResponse(result);
    }

    public List<OrderLineResponse> findAllByOrderId(Long orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
