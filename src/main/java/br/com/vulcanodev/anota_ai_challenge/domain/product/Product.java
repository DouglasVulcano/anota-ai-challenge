package br.com.vulcanodev.anota_ai_challenge.domain.product;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.vulcanodev.anota_ai_challenge.domain.category.Category;
import br.com.vulcanodev.anota_ai_challenge.dtos.ProductDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String title;
    private String ownerId;
    private Category category;
    private Integer price;
    private String description;

    public Product(ProductDto productData) {
        this.title = productData.title();
        this.ownerId = productData.ownerId();
        this.price = productData.price();
        this.description = productData.description();
    }
}
