package com.agility.usermanagement.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HelloWorldController {

    @GetMapping("/")
    public String helloWorld() {
        return "Hello world";
    }

    @GetMapping("/protected")
    public String helloWorldProtected(Principal principal) {
        return "Hello VIP " + principal.getName();
    }
}
