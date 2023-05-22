package com.umer.simplefakebank.controller;

import com.umer.simplefakebank.service.AccountService;

public abstract class BaseAccountController {
    protected final AccountService accountService;

    public BaseAccountController(AccountService accountService) {
        this.accountService = accountService;
    }
}
