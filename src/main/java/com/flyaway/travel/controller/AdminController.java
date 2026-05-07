package com.flyaway.travel.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cleanup")
public class AdminController {

    @PersistenceContext
    private EntityManager entityManager;

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cleanup() {
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE bookings RESTART IDENTITY").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE flights RESTART IDENTITY").executeUpdate();
        entityManager.createNativeQuery("TRUNCATE TABLE users RESTART IDENTITY").executeUpdate();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

        return ResponseEntity.ok().build();
    }
}
