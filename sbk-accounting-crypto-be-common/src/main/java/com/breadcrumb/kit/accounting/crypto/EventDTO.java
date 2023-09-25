package com.breadcrumb.kit.accounting.crypto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDTO {

    /**
     * Transaction, which triggered the Event
     */
    private String sourceTxId;

    /**
     * Transaction, which is affected
     */
    private String targetTxId;

    /**
     * Amount of cryptocurrency assets
     */
    private BigDecimal amount;

    /**
     * Rate at which asset was bought
     */
    private BigDecimal baseRate;

    /**
     * Rate at which asset is being sold
     */
    private BigDecimal rate;

    /**
     * Amount in USD, which is subject to taxation
     */
    private BigDecimal taxableAmountUsd = BigDecimal.ZERO;

    /**
     * There may be different tax % applied to different type of income
     */
    private IncomeType incomeType;

    private CapitalGainType capitalGainType;

    public String getSourceTxId() {
        return sourceTxId;
    }

    public void setSourceTxId(String sourceTxId) {
        this.sourceTxId = sourceTxId;
    }

    public String getTargetTxId() {
        return targetTxId;
    }

    public void setTargetTxId(String targetTxId) {
        this.targetTxId = targetTxId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(BigDecimal baseRate) {
        this.baseRate = baseRate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getTaxableAmountUsd() {
        return taxableAmountUsd;
    }

    public void setTaxableAmountUsd(BigDecimal taxableAmountUsd) {
        this.taxableAmountUsd = taxableAmountUsd;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public void setIncomeType(IncomeType incomeType) {
        this.incomeType = incomeType;
    }

    public CapitalGainType getCapitalGainType() {
        return capitalGainType;
    }

    public void setCapitalGainType(CapitalGainType capitalGainType) {
        this.capitalGainType = capitalGainType;
    }
}
