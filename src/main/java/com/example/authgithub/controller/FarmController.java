package com.example.authgithub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.authgithub.entity.Farm;
import com.example.authgithub.entity.Chicken;
import com.example.authgithub.entity.Cow;
import com.example.authgithub.service.CowService;
import com.example.authgithub.service.FarmService;
import com.example.authgithub.service.ChickenService;


@RestController
@RequestMapping("/farm")
public class FarmController {

    private final FarmService farmService;
    private final CowService cowService;
    private final ChickenService chickenService;


    public FarmController(FarmService farmService, CowService cowService, ChickenService chickenService) {
        this.chickenService = chickenService;
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

    @PostMapping("/chicken")
    public Chicken addChicken(Authentication auth, @RequestBody Map<String, String> body) {
        return chickenService.addChickenToFarm(auth.getName(), body.get("name"));
    }

    @GetMapping("/chicken")
    public List<Chicken> getChickens(Authentication auth) {
        return chickenService.getChickensOfUser(auth.getName());
    }

}
