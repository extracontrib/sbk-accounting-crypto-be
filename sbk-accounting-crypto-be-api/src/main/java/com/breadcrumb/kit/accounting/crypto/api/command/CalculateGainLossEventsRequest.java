package com.breadcrumb.kit.accounting.crypto.api.command;

import com.breadcrumb.kit.accounting.crypto.AccountingMethod;
import com.breadcrumb.kit.accounting.crypto.TransactionDTO;

import java.util.List;

public class CalculateGainLossEventsRequest {

    private String userId;
    private List<TransactionDTO> transactions;
    private AccountingMethod accountingMethod;

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public AccountingMethod getAccountingMethod() {
        return accountingMethod;
    }

    public void setAccountingMethod(AccountingMethod accountingMethod) {
        this.accountingMethod = accountingMethod;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
