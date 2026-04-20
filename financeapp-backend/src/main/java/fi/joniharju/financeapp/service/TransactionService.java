package fi.joniharju.financeapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import fi.joniharju.financeapp.dto.TransactionRequest;
import fi.joniharju.financeapp.dto.TransactionResponse;
import fi.joniharju.financeapp.entity.AppUser;
import fi.joniharju.financeapp.entity.Category;
import fi.joniharju.financeapp.entity.Transaction;
import fi.joniharju.financeapp.mapper.DtoMapper;
import fi.joniharju.financeapp.repository.CategoryRepository;
import fi.joniharju.financeapp.repository.TransactionRepository;

@Service
public class TransactionService {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository, CategoryRepository categoryRepository) {
        this.transactionRepository = transactionRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TransactionResponse> getTransactions(AppUser user) {
        return transactionRepository.findAllByUser(user).stream().map(DtoMapper::toTransactionResponse)
                .collect(Collectors.toList());

    }

    public TransactionResponse createTransaction(TransactionRequest req, AppUser user) {
        Transaction transaction = new Transaction(req.getAmount(), req.getDate(), req.getDescription(), req.getType(),
                user, null);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return DtoMapper.toTransactionResponse(savedTransaction);

    }

    public TransactionResponse updateTransaction(Long id, TransactionRequest req) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        transaction.setAmount(req.getAmount());
        transaction.setDate(req.getDate());
        transaction.setDescription(req.getDescription());
        transaction.setType(req.getType());

        if (req.getCategoryId() != null) {
            Category category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            transaction.setCategory(category);
        } else {
            transaction.setCategory(null);
        }

        return DtoMapper.toTransactionResponse(transactionRepository.save(transaction));
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

}
