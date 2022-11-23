package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistRepository repo;

    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> getArtists() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable Long id) {
        return ResponseEntity.of(repo.findById(id));
    }

    @PostMapping("/artist/")
    public ResponseEntity<Artist> newArtist(@RequestBody Artist a) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repo.save(a));
    }
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@PathVariable Long id,
                                             @RequestBody Artist a){
        return ResponseEntity.of(repo.findById(id)
                .map(old ->{
                    old.setName(a.getName());
                    return Optional.of(repo.save(old));
                }).orElse(Optional.empty())
        );
    }
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
