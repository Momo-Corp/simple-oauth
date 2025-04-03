package com.example.authgithub.service;

import org.springframework.stereotype.Service;

import com.example.authgithub.entity.Cow;
import com.example.authgithub.entity.Farm;

import jakarta.transaction.Transactional;

@Service
public class CowService {
    
    private final FarmService farmService;

    public CowService(FarmService farmService) {
        this.farmService = farmService;
    }

    @Transactional
    public Cow feedCowOfUser(String username) {
        Farm farm = farmService.getOrCreateFarmForUser(username);
        Cow cow = farm.getCow();

        cow.feed();
        return cow;
    }

    @Transactional
    public Cow hungryCowOfUser(String username) {
        Farm farm = farmService.getOrCreateFarmForUser(username);
        Cow cow = farm.getCow();

        cow.hungry();
        return cow;
    }


}
