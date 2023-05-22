package com.umer.simplefakebank.controller;

import com.umer.simplefakebank.service.OperationService;

public abstract class BaseOperationController {
    protected final OperationService operationService;

    public BaseOperationController(OperationService operationService) {
        this.operationService = operationService;
    }
}
