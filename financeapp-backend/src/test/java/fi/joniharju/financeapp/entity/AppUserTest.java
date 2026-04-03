package fi.joniharju.financeapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppUserTest {

    @Test
    void UserCreation() {
        AppUser user = new AppUser("Joni Harju", "test123", "joni@email.com");
        // id is not set until persisted
        assertEquals("Joni Harju", user.getUsername());
        assertEquals("test123", user.getPassword());
        assertEquals("joni@email.com", user.getEmail());

    }

    @Test
    void UserEdit() {
        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        testUser.setId(2L);
        assertEquals(2L, testUser.getId());
        testUser.setUsername("New Name");
        assertEquals("New Name", testUser.getUsername());
        testUser.setPassword("newpass");
        assertEquals("newpass", testUser.getPassword());
        testUser.setEmail("new@email.com");
        assertEquals("new@email.com", testUser.getEmail());
    }
}
