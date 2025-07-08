package br.com.vulcanodev.anota_ai_challenge.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.vulcanodev.anota_ai_challenge.dtos.CategoryDto;
import br.com.vulcanodev.anota_ai_challenge.exceptions.category.CategoryNotFoundException;
import br.com.vulcanodev.anota_ai_challenge.domain.category.Category;
import br.com.vulcanodev.anota_ai_challenge.repositories.CategoryRepository;
import br.com.vulcanodev.anota_ai_challenge.services.aws.AwsSnsService;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AwsSnsService awsSnsService;

    public CategoryService(CategoryRepository categoryRepository, AwsSnsService awsSnsService) {
        this.categoryRepository = categoryRepository;
        this.awsSnsService = awsSnsService;
    }

    public Category insert(CategoryDto categoryData) {
        Category newCategory = new Category(categoryData);
        this.categoryRepository.save(newCategory);
        this.awsSnsService.publish(newCategory.toString());

        return newCategory;
    }

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public Optional<Category> findById(String id) {
        return this.categoryRepository.findById(id);
    }

    public Category update(String id, CategoryDto categoryData) {
        Category categoryToUpdate = this.findById(id).orElseThrow(CategoryNotFoundException::new);

        if (!categoryData.title().isEmpty())
            categoryToUpdate.setTitle(categoryData.title());
        if (!categoryData.description().isEmpty())
            categoryToUpdate.setDescription(categoryData.description());

        this.categoryRepository.save(categoryToUpdate);
        this.awsSnsService.publish(categoryToUpdate.toString());

        return categoryToUpdate;
    }

    public void delete(String id) {
        Category categoryToDelete = this.findById(id).orElseThrow(CategoryNotFoundException::new);
        this.categoryRepository.delete(categoryToDelete);
    }
}
