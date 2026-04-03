package fi.joniharju.financeapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    void testTransactionCreation() {
        AppUser user = new AppUser("Joni Harju", "test123", "joni@email.com");
        Category category = new Category("Groceries", user, null);
        Transaction transaction = new Transaction(new BigDecimal("10.00"), LocalDate.now(), "Test",
                TransactionType.EXPENSE, user, category);
        // id is not set until persisted
        assertEquals(new BigDecimal("10.00"), transaction.getAmount());
        assertEquals("Test", transaction.getDescription());
        assertEquals(TransactionType.EXPENSE, transaction.getType());
        assertEquals(user, transaction.getUser());
        assertEquals(category, transaction.getCategory());
    }

    @Test
    void TransactionEdit() {
        AppUser user = new AppUser("Joni Harju", "test123", "joni@email.com");
        Category category = new Category("Groceries", user, null);
        Transaction transaction = new Transaction(new java.math.BigDecimal("10.50"), java.time.LocalDate.now(),
                "Lunch", TransactionType.EXPENSE, user, category);
        transaction.setAmount(new java.math.BigDecimal("20.00"));
        assertEquals(new java.math.BigDecimal("20.00"), transaction.getAmount());
    }

}
