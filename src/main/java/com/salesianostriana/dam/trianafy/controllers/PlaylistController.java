package com.salesianostriana.dam.trianafy.controllers;

import com.salesianostriana.dam.trianafy.dto.playlistDto.CreatePlaylistDto;
import com.salesianostriana.dam.trianafy.dto.playlistDto.GetPlaylistDto;
import com.salesianostriana.dam.trianafy.dto.playlistDto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlaylistController {
    private final PlaylistRepository repo;
    private final SongRepository songRepo;
    private final PlaylistDtoConverter dtoConverter;

    @GetMapping("/list/")
    public ResponseEntity<List<GetPlaylistDto>> getAllPlaylists(){
        List<Playlist> data = repo.findAll();
        if(data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetPlaylistDto> result = data.stream()
                    .map(GetPlaylistDto::of)
                    .collect(Collectors.toList());

            return ResponseEntity
                    .ok()
                    .body(result);
        }
    }

    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> getPlaylist(@PathVariable Long id){
        return ResponseEntity
                .of(repo.findById(id));
    }

    @PostMapping("/list/")
    public ResponseEntity<CreatePlaylistDto> addPlaylist(@RequestBody CreatePlaylistDto dto){
        Playlist nP = dtoConverter.createPlaylistDtoToPlaylist(dto);

        repo.save(nP);

        CreatePlaylistDto dtoPlaylist = new CreatePlaylistDto(
                nP.getId(),
                nP.getName(),
                nP.getDescription()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoPlaylist);
    }

    @PutMapping("/list/{id}")
    public ResponseEntity<GetPlaylistDto> editPlaylist(@PathVariable Long id, @RequestBody CreatePlaylistDto dto){
        Playlist p = repo.findById(id).map(old ->{
            old.setDescription(dto.getDescription());
            old.setName(dto.getName());
            return repo.save(old);
        }).orElse(null);

        if(p ==null){
            return ResponseEntity.notFound().build();
        }
        GetPlaylistDto getPlaylistDto = dtoConverter.playlistToGetPlaylistDto(p);

        return ResponseEntity.ok(getPlaylistDto);
    }

    @DeleteMapping("/list/{id}")
    public ResponseEntity <?> deletePlaylist(@PathVariable Long id){
        if(repo.existsById(id)){
            repo.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/list/{idList}/song/{idSong}")
    public ResponseEntity<Playlist> addSongToPlaylist(@PathVariable("idList") Long idList, @PathVariable("idSong") Long idSong){
        if(!repo.existsById(idList) && !songRepo.existsById(idSong)){
            return ResponseEntity.notFound().build();
        }else{
            Playlist p = repo.findById(idList).orElse(null);
            Song s = songRepo.findById(idSong).orElse(null);
            p.addSong(s);
            repo.save(p);

            return ResponseEntity.ok(p);
        }
    }
}
