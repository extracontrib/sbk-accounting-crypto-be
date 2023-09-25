package com.breadcrumb.kit.accounting.crypto.service.ledger;

import com.breadcrumb.kit.accounting.crypto.TransactionDTO;
import java.math.BigDecimal;

public class InMemoryFIFOLedger extends AbstractLedger {

    public InMemoryFIFOLedger(boolean includeNonTaxableEvents) {
        super(includeNonTaxableEvents);
    }

    @Override
    protected void addBalance(TransactionDTO transaction) {
        Asset asset = new Asset(transaction.getAmount(), transaction.getRate(),
            transaction.getEffectiveAt(), transaction.getId(), this);
        assets.add(asset);

        publishEvent(asset.getAmount(), asset.getSourceTxId());
    }

    @Override
    protected void removeBalance(TransactionDTO transaction) {
        dispose(transaction, false);
    }

    @Override
    protected void addRevenue(TransactionDTO transaction) {
        Asset asset = new Asset(transaction.getAmount(), transaction.getRate(),
                transaction.getEffectiveAt(), transaction.getId(), this);
        assets.add(asset);

        publishTaxableEvent(asset.getAmount(), asset.getRate(), asset.getSourceTxId());
    }

    @Override
    protected void addExpense(TransactionDTO transaction) {
        dispose(transaction, true);
    }

    void dispose(TransactionDTO transaction, boolean isTaxable) {
        /* Iterate over non-zero balances in stack and decrease, create taxable event */
        BigDecimal disposingAmount = transaction.getAmount().abs();
        while (disposingAmount.compareTo(BigDecimal.ZERO) > 0) {
            Asset holder = assets.peek();
            if (holder == null) {
                System.err.println("No balances in Stack");
                return;
            }

            disposingAmount = holder.dispose(disposingAmount, transaction.getRate(), transaction.getId(), transaction.getEffectiveAt(), isTaxable);

            if (disposingAmount.compareTo(BigDecimal.ZERO) > 0) {
                assets.remove();
            }
        }
    }
}
