package fi.joniharju.financeapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import fi.joniharju.financeapp.entity.TransactionType;

public class TransactionResponse {

    private Long id;
    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private TransactionType type;
    private String categoryName;

    public TransactionResponse() {
    }

    public TransactionResponse(Long id, BigDecimal amount, LocalDate date, String description, TransactionType type,
            String categoryName) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
