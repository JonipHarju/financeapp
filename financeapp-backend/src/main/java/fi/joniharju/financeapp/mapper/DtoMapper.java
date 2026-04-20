package fi.joniharju.financeapp.mapper;

import fi.joniharju.financeapp.dto.CategoryResponse;
import fi.joniharju.financeapp.dto.TransactionResponse;
import fi.joniharju.financeapp.entity.Category;
import fi.joniharju.financeapp.entity.Transaction;

public class DtoMapper {

    // this mapper method makes sure that the client never receives data that it
    // should not receive

    public static TransactionResponse toTransactionResponse(Transaction transaction) {
        String categoryName = transaction.getCategory() != null
                ? transaction.getCategory().getName()
                : null;

        return new TransactionResponse(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription(),
                transaction.getType(),
                categoryName);
    }

    public static CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName());
    }

}
