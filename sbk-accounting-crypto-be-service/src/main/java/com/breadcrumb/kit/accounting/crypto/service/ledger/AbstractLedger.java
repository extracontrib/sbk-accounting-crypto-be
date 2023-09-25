package com.breadcrumb.kit.accounting.crypto.service.ledger;

import com.breadcrumb.kit.accounting.crypto.CapitalGainType;
import com.breadcrumb.kit.accounting.crypto.TransactionDTO;
import com.breadcrumb.kit.accounting.crypto.utils.DateUtils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class AbstractLedger extends AbstractLedgerEventPublisher {

    protected Queue<Asset> assets = new LinkedList<>();

    public AbstractLedger(boolean includeNonTaxableEvents) {
        super(includeNonTaxableEvents);
    }

    protected abstract void addBalance(TransactionDTO transaction);

    protected abstract void removeBalance(TransactionDTO transaction);

    protected abstract void addRevenue(TransactionDTO transaction);

    protected abstract void addExpense(TransactionDTO transaction);

    @Override
    protected CapitalGainType getCapitalGainTypeByPeriod(long targetTxEffectiveAt, long sourceTxEffectiveAt) {
        return DateUtils.monthsBetweenDates(targetTxEffectiveAt, sourceTxEffectiveAt) >= 12 ? CapitalGainType.LONG_TERM: CapitalGainType.SHORT_TERM;
    }

    public void process(List<TransactionDTO> transactions) {

        /* Sort transactions by effectiveAt */
        transactions.sort(Comparator.comparingLong(TransactionDTO::getEffectiveAt));

        transactions.forEach(t -> {
            switch (t.getTransactionType()) {
                case DEPOSIT:
                    addBalance(t);
                    break;

                case EXPENSE:
                    addExpense(t);
                    break;

                case REVENUE:
                    addRevenue(t);
                    break;

                /* WITHDRAWAL */
                default:
                    removeBalance(t);
            }
        });
    }
}
