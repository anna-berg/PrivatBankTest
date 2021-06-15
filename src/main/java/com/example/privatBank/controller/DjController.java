package com.example.privatBank.controller;

import com.example.privatBank.entity.Dj;
import com.example.privatBank.repository.DjRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/dj/")
public class DjController {

    @Autowired
    private DjRepository djRepository;

    @GetMapping(value = "{id}")
    public ResponseEntity<Dj> getDjById(@PathVariable("id") Integer djId) {
        Dj dj = djRepository.getOne(djId);

        if (dj == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dj, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Dj> createDj (@RequestBody Dj dj){
        djRepository.save(dj);
        return new ResponseEntity<Dj>(dj, HttpStatus.OK);
    }
}
