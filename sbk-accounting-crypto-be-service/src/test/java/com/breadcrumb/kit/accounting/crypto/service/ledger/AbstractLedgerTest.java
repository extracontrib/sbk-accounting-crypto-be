package com.breadcrumb.kit.accounting.crypto.service.ledger;

import com.breadcrumb.kit.accounting.crypto.TransactionDTO;
import com.breadcrumb.kit.accounting.crypto.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public abstract class AbstractLedgerTest {

    protected TransactionDTO newTx(BigDecimal amount, BigDecimal rate, TransactionType type, long effectiveAt) {
        TransactionDTO transaction = newTx(amount, rate, type);
        transaction.setEffectiveAt(effectiveAt);
        return transaction;
    }

    protected TransactionDTO newTx(BigDecimal amount, BigDecimal rate, TransactionType type) {
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        TransactionDTO transaction = new TransactionDTO();
        transaction.setEffectiveAt(now);
        transaction.setId(UUID.randomUUID().toString());
        transaction.setAmount(amount);
        transaction.setRate(rate);
        transaction.setTransactionType(type);

        return transaction;
    }
}
