package com.example.authgithub.service;

import com.example.authgithub.entity.Chicken;
import com.example.authgithub.entity.Farm;
import com.example.authgithub.repository.ChickenRepository;
import com.example.authgithub.repository.FarmRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ChickenService {

    private final FarmService farmService;
    private final FarmRepository farmRepository;

    public ChickenService(FarmService farmService, FarmRepository farmRepository) {
        this.farmService = farmService;
        this.farmRepository = farmRepository;
    }

    @Transactional
    public List<Chicken> getChickensOfUser(String username) {
        Farm farm = farmService.getOrCreateFarmForUser(username);
        return farm.getChickens();
    }

    @Transactional
    public Chicken addChickenToFarm(String username, String name) {
        Farm farm = farmService.getOrCreateFarmForUser(username);

        Chicken newChicken = new Chicken();
        newChicken.setName(name);

        farm.getChickens().add(newChicken);
        farmRepository.save(farm); // nécessaire pour persister la nouvelle poule

        return newChicken;
    }

}