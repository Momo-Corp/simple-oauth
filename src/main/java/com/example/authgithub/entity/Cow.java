package com.example.authgithub.entity;


import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Table(name = "Cows")
@Data
public class Cow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean hungry=true;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, 
        orphanRemoval = true)
    @OneToOne
    @JoinColumn(name = "farm_id", nullable = false)
    @JsonBackReference
    private Farm farm;

    public Cow() {}

    public Cow(String name) {
        this.name = name;
    }

    public void feed() {
        this.hungry=false;
    }

    public void hungry() {
        this.hungry=true;
    }

    // Getters et Setters
}
