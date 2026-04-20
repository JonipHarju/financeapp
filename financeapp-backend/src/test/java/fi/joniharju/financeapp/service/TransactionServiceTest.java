package fi.joniharju.financeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fi.joniharju.financeapp.dto.TransactionRequest;
import fi.joniharju.financeapp.dto.TransactionResponse;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;
import fi.joniharju.financeapp.entity.Transaction;
import fi.joniharju.financeapp.entity.TransactionType;
import fi.joniharju.financeapp.repository.AppUserRepository;
import fi.joniharju.financeapp.repository.CategoryRepository;
import fi.joniharju.financeapp.repository.TransactionRepository;

@DataJpaTest
@ActiveProfiles("test")
@Import(TransactionService.class)
public class TransactionServiceTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TransactionService transactionService;

    @Test
    void getTransactions() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        transactionRepository.save(new Transaction(new BigDecimal("10.00"), LocalDate.now(), "Groceries",
                TransactionType.EXPENSE, testUser, null));
        transactionRepository.save(new Transaction(new BigDecimal("500.00"), LocalDate.now(), "Salary",
                TransactionType.INCOME, testUser, null));

        List<TransactionResponse> response = transactionService.getTransactions(testUser);

        assertEquals(2, response.size());
        assertEquals("Groceries", response.get(0).getDescription());
        assertEquals("Salary", response.get(1).getDescription());
    }

    @Test
    void createTransaction() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        TransactionRequest request = new TransactionRequest(new BigDecimal("10.00"), LocalDate.now(), "Groceries",
                TransactionType.EXPENSE, null);

        TransactionResponse response = transactionService.createTransaction(request, testUser);

        assertNotNull(response.getId());
        assertEquals("Groceries", response.getDescription());
        assertEquals(TransactionType.EXPENSE, response.getType());
        assertEquals(new BigDecimal("10.00"), response.getAmount());
    }

    @Test
    void updateTransaction() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        Transaction savedTransaction = transactionRepository.save(new Transaction(new BigDecimal("10.00"),
                LocalDate.now(), "Old description", TransactionType.EXPENSE, testUser, null));
        TransactionRequest request = new TransactionRequest(new BigDecimal("25.00"), LocalDate.now(),
                "Updated description", TransactionType.INCOME, null);

        TransactionResponse response = transactionService.updateTransaction(savedTransaction.getId(), request);

        assertEquals("Updated description", response.getDescription());
        assertEquals(TransactionType.INCOME, response.getType());
        assertEquals(new BigDecimal("25.00"), response.getAmount());
    }

    @Test
    void updateTransactionWithCategory() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        Category savedCategory = categoryRepository.save(new Category("Food", testUser, null));
        Transaction savedTransaction = transactionRepository.save(new Transaction(new BigDecimal("10.00"),
                LocalDate.now(), "Groceries", TransactionType.EXPENSE, testUser, null));
        TransactionRequest request = new TransactionRequest(new BigDecimal("10.00"), LocalDate.now(), "Groceries",
                TransactionType.EXPENSE, savedCategory.getId());

        TransactionResponse response = transactionService.updateTransaction(savedTransaction.getId(), request);

        assertEquals("Food", response.getCategoryName());
    }

    @Test
    void updateTransactionRemovesCategory() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        Category savedCategory = categoryRepository.save(new Category("Food", testUser, null));
        Transaction savedTransaction = transactionRepository.save(new Transaction(new BigDecimal("10.00"),
                LocalDate.now(), "Groceries", TransactionType.EXPENSE, testUser, savedCategory));
        TransactionRequest request = new TransactionRequest(new BigDecimal("10.00"), LocalDate.now(), "Groceries",
                TransactionType.EXPENSE, null);

        TransactionResponse response = transactionService.updateTransaction(savedTransaction.getId(), request);

        assertNull(response.getCategoryName());
    }

    @Test
    void deleteTransaction() {
        AppUser testUser = appUserRepository.save(new AppUser("Joni Harju", "test123", "joni@email.com"));
        Transaction savedTransaction = transactionRepository.save(new Transaction(new BigDecimal("10.00"),
                LocalDate.now(), "Groceries", TransactionType.EXPENSE, testUser, null));

        transactionService.deleteTransaction(savedTransaction.getId());

        Optional<Transaction> deletedTransaction = transactionRepository.findById(savedTransaction.getId());
        assertFalse(deletedTransaction.isPresent());
    }

}
