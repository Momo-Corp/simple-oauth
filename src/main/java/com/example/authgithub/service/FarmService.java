package com.example.authgithub.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.authgithub.entity.Chicken;
import com.example.authgithub.entity.Cow;
import com.example.authgithub.entity.Farm;
import com.example.authgithub.repository.FarmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

         List<String> chickenNames = new ArrayList<>(List.of("Fluffy", "Pecky", "Nugget", "Chickpea", "Eggbert", "Feathers", "BawkBawk",
                "Drumstick", "Cluckles", "Wingman", "Plucky", "Hen Solo", "Chickira",
                "Yolkie", "Beaker"));
         Random random = new Random();

        for (int j = 0; j < 5; j++) {
            Chicken chicken = new Chicken();
            String chickenName = chickenNames.remove(random.nextInt(chickenNames.size())); // Unique name
            chicken.setName(chickenName);
            chicken.setAge(random.nextInt(3) + 1); // Age between 1 and 3
            chicken.setWeight(1.0 + (random.nextDouble() * 1.5)); // Weight between 1.0 and 2.5

            newFarm.addChicken(chicken);
        }

        return farmRepository.save(newFarm);
    }
}
