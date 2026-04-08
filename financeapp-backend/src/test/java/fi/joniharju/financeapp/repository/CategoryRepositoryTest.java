package fi.joniharju.financeapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryTest {

    @Autowired
    private AppUserRepository AppuserRepository;

    @Autowired
    private CategoryRepository CategoryRepository;

    @Test
    void testSaveAndFindCategory() {

        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppuserRepository.save(testUser);

        Category testCategory = new Category("testCategory", savedUser, null);
        Category savedCategory = CategoryRepository.save(testCategory);
        assertNotNull(savedCategory.getId());
        Optional<Category> foundCategory = CategoryRepository.findById(savedCategory.getId());
        assertTrue(foundCategory.isPresent());
        assertEquals("testCategory", foundCategory.get().getName());
        assertEquals(savedUser, foundCategory.get().getUser());

    }

    @Test
    void deleteCategory() {
        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppuserRepository.save(testUser);
        ;

        Category testCategory = new Category("testCategory", savedUser, null);
        Category savedCategory = CategoryRepository.save(testCategory);

        CategoryRepository.deleteById(savedCategory.getId());
        Optional<Category> deletedCategory = CategoryRepository.findById(savedCategory.getId());
        assertFalse(deletedCategory.isPresent());

    }

    @Test
    void editCategory() {
        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppuserRepository.save(testUser);
        AppUser testUser2 = new AppUser("Updated User", "test123", "updated@email.com");
        AppUser savedUser2 = AppuserRepository.save(testUser2);

        Category testCategory = new Category("testCategory", savedUser, null);
        Category savedCategory = CategoryRepository.save(testCategory);

        savedCategory.setName("updatedCategory");
        savedCategory.setUser(savedUser2);

        Optional<Category> updatedCategory = CategoryRepository.findById(savedCategory.getId());
        assertEquals("Updated User", updatedCategory.get().getUser().getUsername());
        assertEquals("updatedCategory", updatedCategory.get().getName());

    }

}
