package com.agility.shopping.cart.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class create fake api to test
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String accessAdminApi() {
        return "This is Admin Api";
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_MEMBER')")
    public String accessUserApi() {
        return "This is User Api";
    }
}
