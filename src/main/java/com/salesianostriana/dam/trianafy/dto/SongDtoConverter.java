package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {
    public Song createSongDtoToSong(CreateSongDto s){
        return new Song (
                s.getTitle(),
                s.getAlbum(),
                s.getYear()
        );
    }

    public GetSongDto songToGetSongDto(Song s){
        String artistName;

        if(s.getArtist() == null){
            artistName = "No existe el artista";
        }else{
            artistName = s.getArtist().getName();
        }

        return GetSongDto
                .builder()
                .id(s.getId())
                .title(s.getTitle())
                .album(s.getAlbum())
                .year(s.getYear())
                .artist(artistName)
                .build();
    }
}
