package com.vw.preferences.infrastructure.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preferences")
public class PreferencesController {

    @GetMapping()
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, World!");
    }
}
