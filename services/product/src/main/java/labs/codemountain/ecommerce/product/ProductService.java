package labs.codemountain.ecommerce.product;

import jakarta.persistence.EntityNotFoundException;
import labs.codemountain.ecommerce.product.dto.ProductPurchaseRequest;
import labs.codemountain.ecommerce.product.dto.ProductPurchaseResponse;
import labs.codemountain.ecommerce.product.dto.ProductRequest;
import labs.codemountain.ecommerce.product.dto.ProductResponse;
import labs.codemountain.ecommerce.product.exception.ProductPurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductResponse createProduct(ProductRequest request) {
        final Product product = mapper.toEntity(request);

        final Product result = repository.save(product);

        return mapper.toResponse(result);
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        final List<Long> productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();

        final List<Product> productList = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != productList.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }

        // TODO: Check if quantities match available quantities before returning purchases.

        return productList.stream()
                .map(mapper::toPurchaseResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse findProductById(Long productId) {
        return repository.findById(productId)
                .map(mapper::toResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("Could not find product with id=[%s]".formatted(productId)
                        ));
    }

    public List<ProductResponse> findAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
