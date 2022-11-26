package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.songDto.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.songDto.GetSongDto;
import com.salesianostriana.dam.trianafy.dto.songDto.SongDtoConverter;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SongController {
    private final SongRepository repo;
    private final SongDtoConverter dtoConverter;
    private final ArtistRepository artistRepo;

    @GetMapping("/song/")
    public ResponseEntity <List<GetSongDto>> getAllSongs(){
        List<Song> data = repo.findAll();
        if(data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetSongDto> result = data.stream()
                    .map(GetSongDto::of)
                    .collect(Collectors.toList());
            return ResponseEntity
                    .ok()
                    .body(result);
        }
    }

    @GetMapping("/song/{id}")
    public ResponseEntity<Song> getSong(@PathVariable Long id){
        return ResponseEntity
                .of(repo.findById(id));
    }

    @PostMapping("/song/")
    public ResponseEntity<GetSongDto> addSong(@RequestBody CreateSongDto dto){
        if(dto.getArtistId() ==null){
            return ResponseEntity.badRequest().build();
        }

        Song newSong = dtoConverter.createSongDtoToSong(dto);

        Artist artist = artistRepo.findById(dto.getArtistId()).orElse(null);

        newSong.setArtist(artist);
        repo.save(newSong);

        GetSongDto getSongDto = dtoConverter.songToGetSongDto(newSong);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(getSongDto);
    }

    @PutMapping("/song/{id}")
    public ResponseEntity<GetSongDto> editSong(@PathVariable Long id, @RequestBody CreateSongDto dto){
        if(dto.getArtistId() ==null){
            return ResponseEntity.badRequest().build();
        }
        Artist artist = artistRepo.findById(dto.getArtistId()).orElse(null);

        Song s = repo.findById(id).map(old ->{
            old.setTitle(dto.getTitle());
            old.setAlbum(dto.getAlbum());
            old.setYear(dto.getYear());
            old.setArtist(artist);
            return repo.save(old);})
                .orElse(null);

        if(s ==null){
         return ResponseEntity.notFound().build();
        }

        GetSongDto getSongDto = dtoConverter.songToGetSongDto(s);

        return ResponseEntity.ok(getSongDto);
    }

    @DeleteMapping("/song/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id){
        if(repo.existsById(id)) {
            repo.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
