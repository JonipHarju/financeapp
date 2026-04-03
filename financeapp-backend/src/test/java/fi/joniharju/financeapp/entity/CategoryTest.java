package fi.joniharju.financeapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void CategoryCreation() {
        AppUser user = new AppUser("Joni Harju", "test123", "joni@email.com");
        Category category = new Category("Groceries", user, null);
        assertEquals("Groceries", category.getName());
        assertEquals(user, category.getUser());
    }

    @Test
    void CategoryEdit() {
        AppUser user = new AppUser("Joni Harju", "test123", "joni@email.com");
        Category category = new Category("Groceries", user, null);
        category.setName("Utilities");
        assertEquals("Utilities", category.getName());
    }

}
