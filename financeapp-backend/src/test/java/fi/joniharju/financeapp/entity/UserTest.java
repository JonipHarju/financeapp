package fi.joniharju.financeapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    void UserCreation() {
        User testUser = new User(1L, "Joni Harju", "test123", "joni@email.com");
        assertEquals(1L, testUser.getId());
        assertEquals("Joni Harju", testUser.getUsername());
        assertEquals("test123", testUser.getPassword());
        assertEquals("joni@email.com", testUser.getEmail());

    }

    @Test
    void UserEdit() {
        User testUser = new User(1L, "Joni Harju", "test123", "joni@email.com");
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
