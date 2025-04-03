package com.example.authgithub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "chickens")
@Data
public class Chicken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrémentation de l'ID
    private Long id;

    private String name;
    private int age;
    private double weight;

    @ManyToOne
    @JoinColumn(name = "farm_id", nullable = false)
    @JsonBackReference
    private Farm farm;

    public Chicken() {
        // Constructeur par défaut requis par JPA
    }

    public Chicken(String name, int age, double weight) {
        this.name = name;
        this.age = age;
        this.weight = weight;
    }

    // Getters et setters si nécessaire (ou utiliser @Data avec Lombok)
}


