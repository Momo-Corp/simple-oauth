package com.example.authgithub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/counter")
public class CounterController {    
    public static int count=0;

    @GetMapping
    public int getCounter() {
        return count;
    }

    @PostMapping("/increment")
    public void incrementCounter() {
        count=count+1;
    }
}

