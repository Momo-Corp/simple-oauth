package com.example.authgithub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.authgithub.entity.Cow;
import com.example.authgithub.entity.Farm;
import com.example.authgithub.repository.FarmRepository;

import java.util.Optional;

@Service
public class FarmService {

    private final FarmRepository farmRepository;

    public FarmService(FarmRepository farmRepository) {
        this.farmRepository = farmRepository;
    }

    @Transactional
    public Farm getOrCreateFarmForUser(String username) {
        // Vérifier si la ferme existe déjà
        Optional<Farm> existingFarm = farmRepository.findById(username);
        if (existingFarm.isPresent()) {
            return existingFarm.get(); // Retourner la ferme existante
        }

        // Sinon, créer une nouvelle ferme
        Farm newFarm = new Farm(username, "My Farm", "Unknown Location");
        Cow newCow = new Cow("Bessie");
        newFarm.setCow(newCow);
        return farmRepository.save(newFarm);
    }
}
