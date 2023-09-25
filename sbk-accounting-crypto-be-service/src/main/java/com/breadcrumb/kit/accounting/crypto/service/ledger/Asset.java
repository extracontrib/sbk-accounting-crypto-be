package com.breadcrumb.kit.accounting.crypto.service.ledger;

import java.math.BigDecimal;

public class Asset {

    private AbstractLedgerEventPublisher ledgerEventPublisher;
    private BigDecimal amount;
    private BigDecimal rate;
    private long effectiveAt;
    private String sourceTxId;

    public Asset(BigDecimal amount, BigDecimal rate, long effectiveAt, String sourceTxId, AbstractLedgerEventPublisher ledgerEventPublisher) {
        this.ledgerEventPublisher = ledgerEventPublisher;
        this.amount = amount;
        this.rate = rate;
        this.effectiveAt = effectiveAt;
        this.sourceTxId = sourceTxId;
    }

    public BigDecimal dispose(BigDecimal disposeAmount, BigDecimal baseRate, String txId, long txEffectiveAt, boolean isTaxable) {
        BigDecimal result = this.amount.subtract(disposeAmount);
        if (result.compareTo(BigDecimal.ZERO) > 0) {
            if (isTaxable) {
                ledgerEventPublisher.publishTaxableEvent(disposeAmount, this.rate, baseRate,
                        txId, txEffectiveAt, sourceTxId, effectiveAt);
            } else {
                ledgerEventPublisher.publishEvent(disposeAmount, txId, sourceTxId);
            }

            this.amount = result;
            return BigDecimal.ZERO;

        } else {
            if (isTaxable) {
                ledgerEventPublisher.publishTaxableEvent(this.amount, this.rate, baseRate,
                        txId, txEffectiveAt, sourceTxId, effectiveAt);
            } else {
                ledgerEventPublisher.publishEvent(this.amount, txId, sourceTxId);
            }

            this.amount = BigDecimal.ZERO;
            return result.abs();
        }
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

    public String getSourceTxId() {
        return sourceTxId;
    }

    public void setSourceTxId(String sourceTxId) {
        this.sourceTxId = sourceTxId;
    }
}
