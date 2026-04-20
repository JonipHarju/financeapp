package fi.joniharju.financeapp.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    public List<TransactionResponse> getTransactions(AppUser user) {
        return transactionRepository.findAllByUser(user).stream().map(DtoMapper::toTransactionResponse)
                .collect(Collectors.toList());

    }

    public TransactionResponse createTransaction(TransactionRequest req, AppUser user) {
        Category category = null;
        if (req.getCategoryId() != null) {
            category = categoryRepository.findById(req.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        }
        Transaction transaction = new Transaction(req.getAmount(), req.getDate(), req.getDescription(), req.getType(),
                user, category);
        return DtoMapper.toTransactionResponse(transactionRepository.save(transaction));
    }

    public TransactionResponse updateTransaction(Long id, TransactionRequest req, AppUser user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

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

    public void deleteTransaction(Long id, AppUser user) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        if (!transaction.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        transactionRepository.deleteById(id);
    }

}
