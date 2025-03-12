package labs.codemountain.ecommerce.product;

import jakarta.validation.Valid;
import labs.codemountain.ecommerce.product.dto.ProductPurchaseRequest;
import labs.codemountain.ecommerce.product.dto.ProductPurchaseResponse;
import labs.codemountain.ecommerce.product.dto.ProductRequest;
import labs.codemountain.ecommerce.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public final ProductResponse createProduct(@RequestBody @Valid ProductRequest request) {
        return service.createProduct(request);
    }

    @PostMapping("/purchase")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public final List<ProductPurchaseResponse> purchaseProducts(@RequestBody List<ProductPurchaseRequest> request) {
        return service.purchaseProducts(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public final ProductResponse getProductById(@PathVariable("id") Long productId) {
        return service.findProductById(productId);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public final List<ProductResponse> getAllProducts() {
        return service.findAllProducts();
    }
}
