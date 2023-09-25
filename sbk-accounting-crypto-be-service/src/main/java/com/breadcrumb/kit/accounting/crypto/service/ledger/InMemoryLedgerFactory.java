package com.breadcrumb.kit.accounting.crypto.service.ledger;

import com.breadcrumb.kit.accounting.crypto.AccountingMethod;
import com.breadcrumb.kit.accounting.crypto.exception.AccountingMethodNotSupportedException;
import com.breadcrumb.kit.accounting.crypto.exception.ApplicationException;

public final class InMemoryLedgerFactory {

    private InMemoryLedgerFactory() {}

    public static AbstractLedger getLedger(AccountingMethod accountingMethod, boolean includeNonTaxableEvents) throws ApplicationException {
        switch (accountingMethod) {
            case FIFO:
                return new InMemoryFIFOLedger(includeNonTaxableEvents);
            default:
                throw new AccountingMethodNotSupportedException(accountingMethod.name() + " not supported");
        }
    }
}
