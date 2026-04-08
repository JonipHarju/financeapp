package fi.joniharju.financeapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Transaction;
import fi.joniharju.financeapp.entity.TransactionType;

@DataJpaTest
@ActiveProfiles("test")
public class TransactionRepositoryTest {

    @Autowired
    private AppUserRepository AppUserRepository;

    @Autowired
    private TransactionRepository TransactionRepository;

    @Test
    void testSaveAndFindTransaction() {
        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppUserRepository.save(testUser);

        Transaction testTransaction = new Transaction(
                new BigDecimal("10.50"), LocalDate.now(), "Test transaction", TransactionType.EXPENSE, savedUser, null);
        Transaction savedTransaction = TransactionRepository.save(testTransaction);
        assertNotNull(savedTransaction.getId());

        Optional<Transaction> foundTransaction = TransactionRepository.findById(savedTransaction.getId());
        assertTrue(foundTransaction.isPresent());
        assertEquals(savedUser, foundTransaction.get().getUser());
    }

    @Test
    void testDeleteTransaction() {
        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppUserRepository.save(testUser);

        Transaction testTransaction = new Transaction(
                new BigDecimal("10.50"), LocalDate.now(), "Test transaction", TransactionType.EXPENSE, savedUser, null);
        Transaction savedTransaction = TransactionRepository.save(testTransaction);

        TransactionRepository.deleteById(savedTransaction.getId());
        Optional<Transaction> deletedTransaction = TransactionRepository.findById(savedTransaction.getId());

        assertFalse(deletedTransaction.isPresent());

    }

    @Test
    void testEditTransaction() {
        AppUser testUser = new AppUser("Joni Harju", "test123", "joni@email.com");
        AppUser savedUser = AppUserRepository.save(testUser);

        Transaction testTransaction = new Transaction(
                new BigDecimal("10.50"), LocalDate.now(), "Test transaction", TransactionType.EXPENSE, savedUser, null);
        Transaction savedTransaction = TransactionRepository.save(testTransaction);

        savedTransaction.setAmount(new BigDecimal("25.75"));
        savedTransaction.setDescription("Updated transaction");
        savedTransaction.setType(TransactionType.INCOME);
        Transaction updatedTransaction = TransactionRepository.save(savedTransaction);

        Optional<Transaction> foundTransaction = TransactionRepository.findById(updatedTransaction.getId());

        assertTrue(foundTransaction.isPresent());
        assertEquals(new BigDecimal("25.75"), foundTransaction.get().getAmount());
        assertEquals("Updated transaction", foundTransaction.get().getDescription());
        assertEquals(TransactionType.INCOME, foundTransaction.get().getType());
    }

}
