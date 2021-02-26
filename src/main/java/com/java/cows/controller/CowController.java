package com.java.cows.controller;

import com.java.cows.model.Cow;
import com.java.cows.repository.CowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//Start command: mvnw spring-boot:run

//for configuring allowed origins
@CrossOrigin(origins = "http://localhost:8080")
//used to define a controller and to indicate that the return value of the methods should be be bound to the web response body.
@RestController
//declares that all Apis’ url in the controller will start with /api
@RequestMapping("/api")

public class CowController {

    // to inject CowRepository to local variable
    @Autowired
    CowRepository cowRepository;

    @GetMapping("/cows")
    public ResponseEntity<List<Cow>> getAllCows(@RequestParam(required = false)String name) {
        try {
            List<Cow> cows = new ArrayList<Cow>();

            if (name == null)
                cowRepository.findAll().forEach(cows::add);
            else
                cowRepository.findByNameContaining(name).forEach(cows::add);

            if (cows.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(cows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cows/{id}")
    public ResponseEntity<Cow> getCowById(@PathVariable("id") String id) {
        Optional<Cow> cowData = cowRepository.findById(id);

        if (cowData.isPresent()) {
            return new ResponseEntity<>(cowData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/cows")
    public ResponseEntity<Cow> createCow(@RequestBody Cow cow) {
        try {
            Cow _cow = cowRepository.save(new Cow(cow.getName(), cow.getGender(), cow.getDescription(), cow.getImage(), false));
            return new ResponseEntity<>(_cow, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/cows/{id}")
    public ResponseEntity<Cow> updateCow(@PathVariable("id") String id, @RequestBody Cow cow) {
        Optional<Cow> cowData = cowRepository.findById(id);

        if (cowData.isPresent()) {
            Cow _cow = cowData.get();
            _cow.setName(cow.getName());
            _cow.setGender(cow.getGender());
            _cow.setDescription(cow.getDescription());
            _cow.setPublished(cow.isPublished());
            _cow.setImage(cow.getImage());
            return new ResponseEntity<>(cowRepository.save(_cow), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/cows/{id}")
    public ResponseEntity<HttpStatus> deleteCow(@PathVariable("id") String id) {
        try {
            cowRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cows")
    public ResponseEntity<HttpStatus> deleteAllCows() {
        try {
            cowRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cows/published")
    public ResponseEntity<List<Cow>> findByPublished() {
        try {
            List<Cow> cows = cowRepository.findByPublished(true);

            if (cows.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(cows, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
