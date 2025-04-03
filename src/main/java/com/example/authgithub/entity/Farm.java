package com.example.authgithub.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "farms")
public class Farm {

    @Id
    private String username; // ID = nom de l'utilisateur loggé

    private String name;
    private String location;

    @OneToMany(mappedBy = "farm", cascade = CascadeType.ALL, 
            orphanRemoval = true)
    @JsonManagedReference
    private List<Chicken> chickens = new ArrayList<>();


    @OneToOne(mappedBy = "farm", cascade = CascadeType.ALL, 
        orphanRemoval = true)
    @JsonManagedReference
    private Cow cow;

    // Constructeurs
    public Farm() {}

    public Farm(String username, String name, String location) {
        this.username = username;
        this.name = name;
        this.location = location;
    }

    // Getters et setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public void addChicken(Chicken chicken) {
        chickens.add(chicken);
        chicken.setFarm(this);
    }


    public void setCow(Cow newCow) {
        newCow.setFarm(this);
        this.cow = newCow;
    }

    public Cow getCow() {
        return this.cow;
    }

    public List<Chicken> getChickens() {
        return chickens;
    }
}
