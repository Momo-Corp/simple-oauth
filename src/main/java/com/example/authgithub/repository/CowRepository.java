package com.example.authgithub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authgithub.entity.Cow;

public interface CowRepository extends JpaRepository<Cow, String> {
}
