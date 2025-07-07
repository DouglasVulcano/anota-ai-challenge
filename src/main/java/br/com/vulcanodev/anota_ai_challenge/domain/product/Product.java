package br.com.vulcanodev.anota_ai_challenge.domain.product;

import java.util.Locale.Category;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
