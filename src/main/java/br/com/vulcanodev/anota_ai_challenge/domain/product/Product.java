package br.com.vulcanodev.anota_ai_challenge.domain.product;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String categoryId;
    private Integer price;
    private String description;

    public Product(ProductDto productData) {
        this.title = productData.title();
        this.ownerId = productData.ownerId();
        this.categoryId = productData.categoryId();
        this.price = productData.price();
        this.description = productData.description();
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("title", this.title);
        json.put("ownerId", this.ownerId);
        json.put("categoryId", this.categoryId);
        json.put("price", this.price);
        json.put("description", this.description);
        json.put("type", "product");

        return json.toString();
    }
}
