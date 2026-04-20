package fi.joniharju.financeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fi.joniharju.financeapp.PasswordEncoderConfig;
import fi.joniharju.financeapp.dto.CategoryRequest;
import fi.joniharju.financeapp.dto.CategoryResponse;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;
import fi.joniharju.financeapp.repository.AppUserRepository;
import fi.joniharju.financeapp.repository.CategoryRepository;

@DataJpaTest
@ActiveProfiles("test")
@Import({ CategoryService.class, PasswordEncoderConfig.class })
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private CategoryService categoryService;

    @Test
    void getCategories() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        categoryRepository.save(new Category("Food", testUser, null));
        categoryRepository.save(new Category("Transport", testUser, null));

        List<CategoryResponse> response = categoryService.getCategories(testUser);

        assertEquals(2, response.size());
        assertEquals("Food", response.get(0).getName());
        assertEquals("Transport", response.get(1).getName());
    }

    @Test
    void createCategory() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        CategoryRequest request = new CategoryRequest("Food");

        CategoryResponse response = categoryService.createCategory(request, testUser);

        assertNotNull(response.getId());
        assertEquals("Food", response.getName());
    }

    @Test
    void updateCategory() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        Category savedCategory = categoryRepository.save(new Category("Food", testUser, null));
        CategoryRequest request = new CategoryRequest("Groceries");

        CategoryResponse response = categoryService.updateCategory(savedCategory.getId(), request, testUser);

        assertEquals("Groceries", response.getName());
        assertEquals(savedCategory.getId(), response.getId());
    }

    @Test
    void deleteCategory() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        Category savedCategory = categoryRepository.save(new Category("Food", testUser, null));

        categoryService.deleteCategory(savedCategory.getId(), testUser);

        Optional<Category> deletedCategory = categoryRepository.findById(savedCategory.getId());
        assertFalse(deletedCategory.isPresent());
    }

}
