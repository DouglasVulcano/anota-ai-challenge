package br.com.vulcanodev.anota_ai_challenge.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vulcanodev.anota_ai_challenge.domain.product.Product;
import br.com.vulcanodev.anota_ai_challenge.dtos.ProductDto;
import br.com.vulcanodev.anota_ai_challenge.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody ProductDto product) {
        Product newProduct = this.productService.insert(product);
        return ResponseEntity.ok().body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> allCategories = this.productService.getAll();
        return ResponseEntity.ok().body(allCategories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody ProductDto product) {
        Product updatedProduct = this.productService.update(id, product);
        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable String id) {
        this.productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
