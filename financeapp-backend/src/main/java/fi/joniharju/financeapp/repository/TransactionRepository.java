package fi.joniharju.financeapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;
import fi.joniharju.financeapp.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findAllByUser(AppUser user);
    List<Transaction> findAllByCategory(Category category);
}
