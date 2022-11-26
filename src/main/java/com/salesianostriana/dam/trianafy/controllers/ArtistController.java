package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
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
    private final SongRepository repoSong;

    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> getAllArtists() {
        return ResponseEntity.ok(repo.findAll());
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
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
    public ResponseEntity<?> deleteArtist(@PathVariable Long id){
        List<Song> songList = repoSong.findAll();
        Artist artist = repo.findById(id).orElse(null);

        if(repo.existsById(id)){
            for(int i =0; i< songList.size(); i++){
                if(songList.get(i).getArtist() == artist){
                    songList.get(i).setArtist(null);
                }
            }
            repo.deleteById(id);
        }


        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
