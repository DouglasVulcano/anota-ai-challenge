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

import br.com.vulcanodev.anota_ai_challenge.domain.category.Category;
import br.com.vulcanodev.anota_ai_challenge.dtos.CategoryDto;
import br.com.vulcanodev.anota_ai_challenge.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> insert(@RequestBody CategoryDto category) {
        Category newCategory = this.categoryService.insert(category);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        List<Category> allCategories = this.categoryService.getAll();
        return ResponseEntity.ok().body(allCategories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable String id, @RequestBody CategoryDto category) {
        Category updatedCategory = this.categoryService.update(id, category);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable String id) {
        this.categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
