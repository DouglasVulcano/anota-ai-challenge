package br.com.vulcanodev.anota_ai_challenge.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.vulcanodev.anota_ai_challenge.dtos.ProductDto;
import br.com.vulcanodev.anota_ai_challenge.exceptions.category.CategoryNotFoundException;
import br.com.vulcanodev.anota_ai_challenge.exceptions.product.ProductNotFoundException;
import br.com.vulcanodev.anota_ai_challenge.domain.category.Category;
import br.com.vulcanodev.anota_ai_challenge.domain.product.Product;
import br.com.vulcanodev.anota_ai_challenge.repositories.ProductRepository;
import br.com.vulcanodev.anota_ai_challenge.services.aws.AwsSnsService;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final AwsSnsService awsSnsService;

    public ProductService(ProductRepository productRepository, CategoryService categoryService,
            AwsSnsService awsSnsService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.awsSnsService = awsSnsService;
    }

    public Product insert(ProductDto productData) {
        Category category = this.categoryService.findById(productData.categoryId())
                .orElseThrow(CategoryNotFoundException::new);

        Product newProduct = new Product(productData);
        newProduct.setCategory(category);
        this.productRepository.save(newProduct);
        this.awsSnsService.publish(newProduct.getOwnerId());
        return newProduct;

    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public Optional<Product> findById(String id) {
        return this.productRepository.findById(id);
    }

    public Product update(String id, ProductDto productData) {
        Product productToUpdate = this.findById(id).orElseThrow(ProductNotFoundException::new);

        if (!productData.title().isEmpty())
            productToUpdate.setTitle(productData.title());
        if (!productData.description().isEmpty())
            productToUpdate.setDescription(productData.description());
        if (productData.price() != null)
            productToUpdate.setPrice(productData.price());
        if (productData.categoryId() != null)
            this.categoryService.findById(productData.categoryId())
                    .ifPresent(category -> productToUpdate.setCategory(category));

        this.productRepository.save(productToUpdate);
        this.awsSnsService.publish(productToUpdate.getOwnerId());

        return productToUpdate;
    }

    public void delete(String id) {
        Product productToDelete = this.findById(id).orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(productToDelete);
    }
}
