package fi.joniharju.financeapp.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TransactionTest {

    @Test
    void TransactionCreation() {
        User user = new User(1L, "Joni Harju", "test123", "joni@email.com");
        Category category = new Category(1L, "Groceries", user, null);
        Transaction transaction = new Transaction(1L, new java.math.BigDecimal("10.50"), java.time.LocalDate.now(),
                "Wolt", TransactionType.EXPENSE, user, category);
        assertEquals(1L, transaction.getId());
        assertEquals(new java.math.BigDecimal("10.50"), transaction.getAmount());
        assertEquals("Wolt", transaction.getDescription());
        assertEquals(TransactionType.EXPENSE, transaction.getType());
        assertEquals(user, transaction.getUser());
        assertEquals(category, transaction.getCategory());
    }

    @Test
    void TransactionEdit() {
        User user = new User(1L, "Joni Harju", "test123", "joni@email.com");
        Category category = new Category(1L, "Groceries", user, null);
        Transaction transaction = new Transaction(1L, new java.math.BigDecimal("10.50"), java.time.LocalDate.now(),
                "Lunch", TransactionType.EXPENSE, user, category);
        transaction.setAmount(new java.math.BigDecimal("20.00"));
        assertEquals(new java.math.BigDecimal("20.00"), transaction.getAmount());
    }

}
