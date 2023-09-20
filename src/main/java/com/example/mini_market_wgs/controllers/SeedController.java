package com.example.mini_market_wgs.controllers;

import com.example.mini_market_wgs.utilities.Seed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seeds")
public class SeedController {
    @Autowired
    private Seed seed;

    @PostMapping("")
    public ResponseEntity addTransaction() {
        return ResponseEntity.status(HttpStatus.CREATED).body(seed.allSeed());
    }
}
