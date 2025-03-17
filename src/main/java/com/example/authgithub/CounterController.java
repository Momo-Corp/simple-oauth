package com.example.authgithub;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/counter")
public class CounterController {    

    private final UserService userService;
    public static int count=0;

    public CounterController(UserService userService) {
        this.userService=userService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public int getCounter() {
        return count;
    }

    @PostMapping("/increment")
    @PreAuthorize("isAuthenticated()")
    public void incrementCounter(Authentication authentication) {
        count=count+1;
    }
}

