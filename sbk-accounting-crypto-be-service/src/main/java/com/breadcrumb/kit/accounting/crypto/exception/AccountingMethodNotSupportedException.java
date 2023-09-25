package com.breadcrumb.kit.accounting.crypto.exception;

public class AccountingMethodNotSupportedException extends ApplicationException {

    public AccountingMethodNotSupportedException(String message) {
        super(message, "error_accounting_method_not_supported");
    }
}
