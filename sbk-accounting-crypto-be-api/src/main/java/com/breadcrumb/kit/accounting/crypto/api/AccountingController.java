package com.breadcrumb.kit.accounting.crypto.api;

import com.breadcrumb.kit.accounting.crypto.EventDTO;
import com.breadcrumb.kit.accounting.crypto.api.command.CalculateGainLossEventsRequest;
import com.breadcrumb.kit.accounting.crypto.api.command.CalculateGainLossEventsResponse;
import com.breadcrumb.kit.accounting.crypto.exception.ApplicationException;
import com.breadcrumb.kit.accounting.crypto.service.AccountingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountingController {

    @Autowired
    private AccountingService accountingService;

    @PostMapping(value = "/api/reports/calculate-gain-loss-events", produces = "application/json")
    public CalculateGainLossEventsResponse calculateGainLossEvents(
            HttpServletRequest request, @RequestParam(value = "trace_id", required = false) String traceId,
            @RequestParam(name = "include_non_taxable", defaultValue = "false") boolean includeNonTaxableEvents,
            @Valid @RequestBody CalculateGainLossEventsRequest command) throws ApplicationException
    {
        List<EventDTO> events = accountingService.calculateGainLossEvents(command.getTransactions(), command.getAccountingMethod(), includeNonTaxableEvents);
        return new CalculateGainLossEventsResponse(command.getUserId(), command.getAccountingMethod(), events);
    }
}
