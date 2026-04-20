package fi.joniharju.financeapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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

    @Test
    void testFindAllByUser() {
        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppuserRepository.save(testUser);

        CategoryRepository.save(new Category("Food", savedUser, null));
        CategoryRepository.save(new Category("Transport", savedUser, null));

        List<Category> categories = CategoryRepository.findAllByUser(savedUser);

        assertEquals(2, categories.size());
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Food")));
        assertTrue(categories.stream().anyMatch(c -> c.getName().equals("Transport")));
    }

    @Test
    void testFindAllByUserOnlyReturnsOwnCategories() {
        AppUser user1 = AppuserRepository.save(new AppUser("User One", "pass1", "one@email.com"));
        AppUser user2 = AppuserRepository.save(new AppUser("User Two", "pass2", "two@email.com"));

        CategoryRepository.save(new Category("Food", user1, null));
        CategoryRepository.save(new Category("Travel", user2, null));

        List<Category> user1Categories = CategoryRepository.findAllByUser(user1);

        assertEquals(1, user1Categories.size());
        assertEquals("Food", user1Categories.get(0).getName());
    }

}
