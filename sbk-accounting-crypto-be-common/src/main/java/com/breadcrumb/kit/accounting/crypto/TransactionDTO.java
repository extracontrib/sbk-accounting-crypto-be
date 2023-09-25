package com.breadcrumb.kit.accounting.crypto;

import java.math.BigDecimal;

public class TransactionDTO {

    private String id;
    private BigDecimal amount;
    private BigDecimal rate;
    private long effectiveAt;
    private TransactionType transactionType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public long getEffectiveAt() {
        return effectiveAt;
    }

    public void setEffectiveAt(long effectiveAt) {
        this.effectiveAt = effectiveAt;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}
