package com.breadcrumb.kit.accounting.crypto.service.ledger;

import com.breadcrumb.kit.accounting.crypto.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class InMemoryFIFOLedgerTest extends AbstractLedgerTest {

    private AbstractLedger ledger;

    /* Friday, March 25, 2022 1:18:46 PM */
    private final TransactionDTO TX_LONG_TERM_DEPOSIT_100_by_2 = newTx(BigDecimal.valueOf(100), BigDecimal.valueOf(0.0000000000000002), TransactionType.DEPOSIT, 1648214326);
    private final TransactionDTO TX_DEPOSIT_100_by_2 = newTx(BigDecimal.valueOf(100), BigDecimal.valueOf(0.0000000000000002), TransactionType.DEPOSIT);
    private final TransactionDTO TX_DEPOSIT_100_by_1 = newTx(BigDecimal.valueOf(100), BigDecimal.valueOf(0.0000000000000001), TransactionType.DEPOSIT);
    private final TransactionDTO TX_WITHDRAW_5_by_4 = newTx(BigDecimal.valueOf(5), BigDecimal.valueOf(0.0000000000000004), TransactionType.WITHDRAWAL);
    private final TransactionDTO TX_REVENUE_20_by_5 = newTx(BigDecimal.valueOf(20), BigDecimal.valueOf(0.0000000000000005), TransactionType.REVENUE);
    private final TransactionDTO TX_EXPENSE_2_by_5 = newTx(BigDecimal.valueOf(2), BigDecimal.valueOf(0.0000000000000005), TransactionType.EXPENSE);
    private final TransactionDTO TX_EXPENSE_150_by_3 = newTx(BigDecimal.valueOf(150), BigDecimal.valueOf(0.0000000000000003), TransactionType.EXPENSE);

    @Before
    public void setUp() {
        ledger = new InMemoryFIFOLedger(true);
    }

    @Test
    public void test_create_non_taxable_event_for_deposit() {
        ledger.addBalance(TX_DEPOSIT_100_by_2);
        EventDTO event = ledger.getEvents().get(0);

        assertEquals(TX_DEPOSIT_100_by_2.getId(), event.getSourceTxId());
        assertEquals(TX_DEPOSIT_100_by_2.getAmount(), event.getAmount());
        assertEquals(BigDecimal.ZERO, event.getTaxableAmountUsd());
        assertNull(event.getIncomeType());
        assertNull(event.getCapitalGainType());
    }

    @Test
    public void test_create_non_taxable_event_for_withdrawal() {
        ledger.addBalance(TX_DEPOSIT_100_by_2);
        ledger.removeBalance(TX_WITHDRAW_5_by_4);

        EventDTO event = ledger.getEvents().get(1);

        assertEquals(TX_WITHDRAW_5_by_4.getId(), event.getSourceTxId());
        assertEquals(TX_WITHDRAW_5_by_4.getAmount(), event.getAmount());
        assertEquals(BigDecimal.ZERO, event.getTaxableAmountUsd());
        assertNull(event.getIncomeType());
        assertNull(event.getCapitalGainType());
    }

    @Test
    public void test_create_taxable_event_for_revenue() {
        ledger.addRevenue(TX_REVENUE_20_by_5);
        EventDTO event = ledger.getEvents().get(0);

        assertEquals(TX_REVENUE_20_by_5.getId(), event.getSourceTxId());
        assertEquals(TX_REVENUE_20_by_5.getAmount(), event.getAmount());
        assertEquals(TX_REVENUE_20_by_5.getRate(), event.getRate());

        assertTrue(event.getTaxableAmountUsd().compareTo(BigDecimal.ZERO) != 0);
        assertEquals(TX_REVENUE_20_by_5.getAmount().multiply(TX_REVENUE_20_by_5.getRate()), event.getTaxableAmountUsd());
        assertEquals(IncomeType.ORDINARY_INCOME, event.getIncomeType());
        assertNull(event.getCapitalGainType());
    }

    @Test
    public void test_create_taxable_event_for_expense() {
        ledger.addBalance(TX_DEPOSIT_100_by_2);
        ledger.addExpense(TX_EXPENSE_2_by_5);

        EventDTO event = ledger.getEvents().get(1);

        assertEquals(TX_DEPOSIT_100_by_2.getId(), event.getTargetTxId());
        assertEquals(TX_EXPENSE_2_by_5.getId(), event.getSourceTxId());

        assertEquals(TX_EXPENSE_2_by_5.getAmount(), event.getAmount());
        assertEquals(TX_EXPENSE_2_by_5.getRate(), event.getRate());
        assertEquals(TX_DEPOSIT_100_by_2.getRate(), event.getBaseRate());

        assertTrue(event.getTaxableAmountUsd().compareTo(BigDecimal.ZERO) != 0);
        assertEquals(TX_EXPENSE_2_by_5.getAmount().multiply(TX_EXPENSE_2_by_5.getRate())
                .subtract(TX_EXPENSE_2_by_5.getAmount().multiply(TX_DEPOSIT_100_by_2.getRate())), event.getTaxableAmountUsd());

        assertEquals(IncomeType.CAPITAL_GAIN, event.getIncomeType());
        assertEquals(CapitalGainType.SHORT_TERM, event.getCapitalGainType());
    }

    @Test
    public void test_create_taxable_event_for_expense_long_term_capital_gain() {
        ledger.addBalance(TX_LONG_TERM_DEPOSIT_100_by_2);
        ledger.addExpense(TX_EXPENSE_2_by_5);

        EventDTO event = ledger.getEvents().get(1);

        assertEquals(TX_LONG_TERM_DEPOSIT_100_by_2.getId(), event.getTargetTxId());
        assertEquals(TX_EXPENSE_2_by_5.getId(), event.getSourceTxId());

        assertEquals(TX_EXPENSE_2_by_5.getAmount(), event.getAmount());
        assertEquals(TX_EXPENSE_2_by_5.getRate(), event.getRate());
        assertEquals(TX_LONG_TERM_DEPOSIT_100_by_2.getRate(), event.getBaseRate());

        assertTrue(event.getTaxableAmountUsd().compareTo(BigDecimal.ZERO) != 0);
        assertEquals(TX_EXPENSE_2_by_5.getAmount().multiply(TX_EXPENSE_2_by_5.getRate())
                .subtract(TX_EXPENSE_2_by_5.getAmount().multiply(TX_LONG_TERM_DEPOSIT_100_by_2.getRate())), event.getTaxableAmountUsd());

        assertEquals(IncomeType.CAPITAL_GAIN, event.getIncomeType());
        assertEquals(CapitalGainType.LONG_TERM, event.getCapitalGainType());
    }

    @Test
    public void test_multiple_assets_disposition() {
        ledger.addBalance(TX_DEPOSIT_100_by_2);
        ledger.addBalance(TX_DEPOSIT_100_by_1);
        ledger.addExpense(TX_EXPENSE_150_by_3); /* will create two events */

        EventDTO event = ledger.getEvents().get(2);
        assertEquals(TX_DEPOSIT_100_by_2.getId(), event.getTargetTxId());
        assertEquals(TX_EXPENSE_150_by_3.getId(), event.getSourceTxId());
        assertEquals(BigDecimal.valueOf(100), event.getAmount());
        assertEquals(TX_EXPENSE_150_by_3.getRate(), event.getRate());
        assertEquals(TX_DEPOSIT_100_by_2.getRate(), event.getBaseRate());
        assertTrue(event.getTaxableAmountUsd().compareTo(BigDecimal.ZERO) != 0);
        assertEquals(IncomeType.CAPITAL_GAIN, event.getIncomeType());
        assertEquals(CapitalGainType.SHORT_TERM, event.getCapitalGainType());

        assertEquals(BigDecimal.valueOf(100).multiply(TX_EXPENSE_150_by_3.getRate())
                .subtract(BigDecimal.valueOf(100).multiply(TX_DEPOSIT_100_by_2.getRate())), event.getTaxableAmountUsd());

        event = ledger.getEvents().get(3);
        assertEquals(TX_DEPOSIT_100_by_1.getId(), event.getTargetTxId());
        assertEquals(TX_EXPENSE_150_by_3.getId(), event.getSourceTxId());
        assertEquals(BigDecimal.valueOf(50), event.getAmount());
        assertEquals(TX_EXPENSE_150_by_3.getRate(), event.getRate());
        assertEquals(TX_DEPOSIT_100_by_1.getRate(), event.getBaseRate());
        assertTrue(event.getTaxableAmountUsd().compareTo(BigDecimal.ZERO) != 0);
        assertEquals(IncomeType.CAPITAL_GAIN, event.getIncomeType());
        assertEquals(CapitalGainType.SHORT_TERM, event.getCapitalGainType());

        assertEquals(BigDecimal.valueOf(50).multiply(TX_EXPENSE_150_by_3.getRate())
                .subtract(BigDecimal.valueOf(50).multiply(TX_DEPOSIT_100_by_1.getRate())), event.getTaxableAmountUsd());
    }
}
