package com.umer.simplefakebank.controller;

import com.umer.simplefakebank.service.UserService;
import com.umer.simplefakebank.repsitory.UserRepository;

public abstract class BaseUserController {
    protected final UserService userService;
    protected final UserRepository userRepository;

    public BaseUserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
}
