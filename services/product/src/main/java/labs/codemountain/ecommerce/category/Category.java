package labs.codemountain.ecommerce.category;

import jakarta.persistence.*;
import labs.codemountain.ecommerce.product.Product;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
