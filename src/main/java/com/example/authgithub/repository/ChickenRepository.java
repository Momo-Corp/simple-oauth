package com.example.authgithub.repository;

import com.example.authgithub.entity.Chicken;
import com.example.authgithub.entity.Farm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChickenRepository extends JpaRepository<Chicken, Long> {
    Page<Chicken> findAll(Pageable pageable);  // Ajout de la pagination
    Page<Chicken> findAllByFarm(Farm farm, Pageable pageable);
}
