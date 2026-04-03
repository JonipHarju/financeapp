package fi.joniharju.financeapp.repository;

import org.springframework.data.repository.CrudRepository;

import fi.joniharju.financeapp.entity.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
