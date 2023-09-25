package com.breadcrumb.kit.accounting.crypto.service.ledger;

import com.breadcrumb.kit.accounting.crypto.CapitalGainType;
import com.breadcrumb.kit.accounting.crypto.EventDTO;
import com.breadcrumb.kit.accounting.crypto.IncomeType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractLedgerEventPublisher {

    private boolean includeNonTaxableEvents;
    protected List<EventDTO> events = new ArrayList<>();

    public AbstractLedgerEventPublisher(boolean includeNonTaxableEvents) {
        this.includeNonTaxableEvents = includeNonTaxableEvents;
    }

    void publishEvent(BigDecimal amount, String sourceTxId) {
        if (!includeNonTaxableEvents) {
            return;
        }

        EventDTO event = new EventDTO();
        event.setAmount(amount);
        event.setSourceTxId(sourceTxId);
        events.add(event);
    }

    void publishEvent(BigDecimal amount, String sourceTxId, String targetTxId) {
        if (!includeNonTaxableEvents) {
            return;
        }

        EventDTO event = new EventDTO();
        event.setAmount(amount);
        event.setSourceTxId(sourceTxId);
        event.setTargetTxId(targetTxId);
        events.add(event);
    }

    void publishTaxableEvent(BigDecimal amount, BigDecimal rate, String sourceTxId) {
        EventDTO event = new EventDTO();
        event.setRate(rate);
        event.setAmount(amount);
        event.setSourceTxId(sourceTxId);
        event.setTaxableAmountUsd(amount.multiply(rate));
        event.setIncomeType(IncomeType.ORDINARY_INCOME);
        events.add(event);
    }

    void publishTaxableEvent(BigDecimal amount, BigDecimal baseRate, BigDecimal rate,
                             String sourceTxId, long sourceTxEffectiveAt,
                             String targetTxId, long targetTxEffectiveAt) {

        EventDTO event = new EventDTO();
        event.setBaseRate(baseRate);
        event.setRate(rate);
        event.setAmount(amount);
        event.setSourceTxId(sourceTxId);
        event.setTargetTxId(targetTxId);
        event.setTaxableAmountUsd(amount.multiply(rate).subtract(amount.multiply(baseRate)));
        event.setIncomeType(IncomeType.CAPITAL_GAIN);
        event.setCapitalGainType(getCapitalGainTypeByPeriod(targetTxEffectiveAt, sourceTxEffectiveAt));
        events.add(event);
    }

    protected abstract CapitalGainType getCapitalGainTypeByPeriod(long targetTxEffectiveAt, long sourceTxEffectiveAt);

    public List<EventDTO> getEvents() {
        return events;
    }
}
