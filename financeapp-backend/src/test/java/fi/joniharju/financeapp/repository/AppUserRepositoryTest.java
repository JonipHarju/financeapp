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

@DataJpaTest
@ActiveProfiles("test")
public class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository AppuserRepository;

    @Test
    void testSaveAndFindUser() {

        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppuserRepository.save(testUser);
        assertNotNull(savedUser.getId());
        Optional<AppUser> foundUser = AppuserRepository.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals("Joni Harju", foundUser.get().getUsername());
        assertEquals("joni@email.com", foundUser.get().getEmail());

    }

    @Test
    void testDeleteUser() {

        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppuserRepository.save(testUser);

        Optional<AppUser> foundUser = AppuserRepository.findById(savedUser.getId());
        assertTrue(foundUser.isPresent());
        AppuserRepository.deleteById(savedUser.getId());
        Optional<AppUser> deletedUser = AppuserRepository.findById(savedUser.getId());
        assertFalse(deletedUser.isPresent());

    }

    @Test
    void testEditUser() {

        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppuserRepository.save(testUser);

        savedUser.setUsername("Joni Updated");
        savedUser.setEmail("joni.updated@email.com");
        AppuserRepository.save(savedUser);

        Optional<AppUser> updatedUser = AppuserRepository.findById(savedUser.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals("Joni Updated", updatedUser.get().getUsername());
        assertEquals("joni.updated@email.com", updatedUser.get().getEmail());

    }
}
