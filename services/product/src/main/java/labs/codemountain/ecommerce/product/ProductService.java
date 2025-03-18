package labs.codemountain.ecommerce.product;

import labs.codemountain.ecommerce.product.dto.ProductPurchaseRequest;
import labs.codemountain.ecommerce.product.dto.ProductPurchaseResponse;
import labs.codemountain.ecommerce.product.dto.ProductRequest;
import labs.codemountain.ecommerce.product.dto.ProductResponse;
import labs.codemountain.ecommerce.product.exceptions.ProductNotFoundException;
import labs.codemountain.ecommerce.product.exceptions.ProductPurchaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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

        List<ProductPurchaseRequest> sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        List<ProductPurchaseResponse> purchaseList = new ArrayList<>();

        for (int i = 0; i < productList.size(); i++) {
            final Product product = productList.get(i);
            final ProductPurchaseRequest purchaseRequest = sortedRequest.get(i);

            if (product.getQuantity() < purchaseRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with id=[%s]".formatted(product.getId()));
            }

            final int updatedProductQuantity = product.getQuantity() - purchaseRequest.quantity();
            product.setQuantity(updatedProductQuantity);
            repository.save(product);

            purchaseList.add(mapper.toPurchaseResponse(product));
        }

        return purchaseList;
    }

    public ProductResponse findProductById(Long productId) {
        return repository.findById(productId)
                .map(mapper::toResponse)
                .orElseThrow(
                        () -> new ProductNotFoundException("Could not find product with id=[%s]".formatted(productId)
                        ));
    }

    public List<ProductResponse> findAllProducts() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
