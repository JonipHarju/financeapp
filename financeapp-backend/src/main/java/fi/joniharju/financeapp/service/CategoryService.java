package fi.joniharju.financeapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joniharju.financeapp.dto.CategoryRequest;
import fi.joniharju.financeapp.dto.CategoryResponse;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;
import fi.joniharju.financeapp.mapper.DtoMapper;
import fi.joniharju.financeapp.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryResponse> getCategories(AppUser user) {
        return categoryRepository.findAllByUser(user).stream().map(DtoMapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse createCategory(CategoryRequest req, AppUser user) {
        Category category = new Category(req.getName(), user, null);
        Category savedCategory = categoryRepository.save(category);
        return DtoMapper.toCategoryResponse(savedCategory);
    }

    public CategoryResponse updateCategory(Long id, CategoryRequest req) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(req.getName());
        return DtoMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
