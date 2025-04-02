package com.example.authgithub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.authgithub.entity.Farm;
import com.example.authgithub.entity.Cow;
import com.example.authgithub.service.CowService;
import com.example.authgithub.service.FarmService;

@RestController
@RequestMapping("/farm")
public class FarmController {

    private final FarmService farmService;
    private final CowService cowService;

    public FarmController(FarmService farmService, CowService cowService) {
        this.farmService = farmService;
        this.cowService = cowService;
    }

    @GetMapping
    public Farm getFarm(Authentication authentication) {
        String username = authentication.getName();
        return farmService.getOrCreateFarmForUser(username);
    }

    @GetMapping("/cow")
    public Cow getCow(Authentication authentication) {
        String username = authentication.getName();
        Farm farm = farmService.getOrCreateFarmForUser(username);
        return farm.getCow();
    }

    @PostMapping("/cow/feed")
    public Cow feedCow(Authentication authentication) {
        return cowService.feedCowOfUser(authentication.getName());
    }

    @PostMapping("/cow/hungry")
    public Cow hungryCow(Authentication authentication) {
        return cowService.hungryCowOfUser(authentication.getName());
    }

}
