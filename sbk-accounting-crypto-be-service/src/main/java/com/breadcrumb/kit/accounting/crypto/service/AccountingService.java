package com.breadcrumb.kit.accounting.crypto.service;

import com.breadcrumb.kit.accounting.crypto.AccountingMethod;
import com.breadcrumb.kit.accounting.crypto.EventDTO;
import com.breadcrumb.kit.accounting.crypto.TransactionDTO;
import com.breadcrumb.kit.accounting.crypto.exception.AccountingMethodNotSupportedException;
import com.breadcrumb.kit.accounting.crypto.exception.ApplicationException;
import com.breadcrumb.kit.accounting.crypto.service.ledger.AbstractLedger;
import com.breadcrumb.kit.accounting.crypto.service.ledger.InMemoryLedgerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountingService {

    public List<EventDTO> calculateGainLossEvents(List<TransactionDTO> transactions, AccountingMethod accountingMethod, boolean includeNonTaxableEvents)
            throws ApplicationException {

        if (List.of(AccountingMethod.HIFO, AccountingMethod.LIFO).contains(accountingMethod)) {
            throw new AccountingMethodNotSupportedException(accountingMethod.name() + " method not supported");
        }

        AbstractLedger ledger = InMemoryLedgerFactory.getLedger(accountingMethod, includeNonTaxableEvents);
        ledger.process(transactions);

        return ledger.getEvents();
    }
}
