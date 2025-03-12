package labs.codemountain.ecommerce.product;

import labs.codemountain.ecommerce.category.Category;
import labs.codemountain.ecommerce.product.dto.ProductPurchaseResponse;
import labs.codemountain.ecommerce.product.dto.ProductRequest;
import labs.codemountain.ecommerce.product.dto.ProductResponse;

public class ProductMapper {

    public final Product toEntity(ProductRequest request) {
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .quantity(request.quantity())
                .price(request.price())
                .category(Category.builder()
                        .id(request.categoryId())
                        .build())
                .build();
    }

    public ProductResponse toResponse(Product result) {
        return ProductResponse.builder()
                .id(result.getId())
                .name(result.getName())
                .description(result.getDescription())
                .quantity(result.getQuantity())
                .price(result.getPrice())
                .categoryId(result.getCategory().getId())
                .categoryName(result.getCategory().getName())
                .categoryDescription(result.getCategory().getDescription())
                .build();
    }

    public ProductPurchaseResponse toPurchaseResponse(Product product) {
        return ProductPurchaseResponse.builder().build();
    }
}
