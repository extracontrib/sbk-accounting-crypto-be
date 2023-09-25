package com.breadcrumb.kit.accounting.crypto.api.command;

import com.breadcrumb.kit.accounting.crypto.AccountingMethod;
import com.breadcrumb.kit.accounting.crypto.EventDTO;

import java.util.List;

public class CalculateGainLossEventsResponse {

    private String userId;
    private AccountingMethod accountingMethod;
    private List<EventDTO> events;

    public CalculateGainLossEventsResponse(String userId, AccountingMethod accountingMethod, List<EventDTO> events) {
        this.userId = userId;
        this.accountingMethod = accountingMethod;
        this.events = events;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public AccountingMethod getAccountingMethod() {
        return accountingMethod;
    }

    public void setAccountingMethod(AccountingMethod accountingMethod) {
        this.accountingMethod = accountingMethod;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public void setEvents(List<EventDTO> events) {
        this.events = events;
    }
}
