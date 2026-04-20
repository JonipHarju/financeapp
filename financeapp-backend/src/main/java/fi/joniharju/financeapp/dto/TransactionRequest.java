package fi.joniharju.financeapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import fi.joniharju.financeapp.entity.TransactionType;

public class TransactionRequest {

    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private TransactionType type;
    private Long categoryId;

    public TransactionRequest() {
    }

    public TransactionRequest(BigDecimal amount, LocalDate date, String description, TransactionType type,
            Long categoryId) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}
