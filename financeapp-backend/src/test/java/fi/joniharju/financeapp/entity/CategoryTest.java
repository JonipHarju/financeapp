package fi.joniharju.financeapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void CategoryCreation() {
        User user = new User(1L, "Joni Harju", "test123", "joni@email.com");
        Category category = new Category(1L, "Groceries", user, null);
        assertEquals(1L, category.getId());
        assertEquals("Groceries", category.getName());
        assertEquals(user, category.getUser());
    }

    @Test
    void CategoryEdit() {
        User user = new User(1L, "Joni Harju", "test123", "joni@email.com");
        Category category = new Category(1L, "Groceries", user, null);
        category.setName("Utilities");
        assertEquals("Utilities", category.getName());
    }

}
