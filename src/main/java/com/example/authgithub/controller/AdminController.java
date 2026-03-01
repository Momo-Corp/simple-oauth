package com.example.authgithub.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.authgithub.service.FarmService;

import java.util.Map;

/**
 * AdminController - Endpoints réservés aux administrateurs
 * 
 * 🎓 EXERCICE: Complétez les endpoints pour faire passer les tests
 */
@RestController
@RequestMapping("/admin")
@PreAuthorize("isAuthenticated()")
public class AdminController {

    private final FarmService farmService;

    public AdminController(FarmService farmService) {
        this.farmService = farmService;
    }

    @GetMapping("/stats")
    public Map<String, Object> adminStats() {
        return Map.of(
            "total_farms", 1,
            "total_users", 1
        );
    }
}
