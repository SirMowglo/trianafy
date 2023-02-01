package com.salesianostriana.dam.trianafy.service;


import com.salesianostriana.dam.trianafy.exception.ArtistNotFoundException;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository repository;


    public Artist add(Artist artist) {

        return repository.save(artist);
    }

    public Artist findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new ArtistNotFoundException(id));
    }

    public List<Artist> findAll() {

        List<Artist> result = repository.findAll();

        if(result.isEmpty()){
            throw new ArtistNotFoundException();
        }

        return result;
    }

    public Artist edit(Long id, Artist artist) {
        return repository.findById(id)
                .map(old ->{
                    old.setName(artist.getName());
                    return repository.save(old);
                }).orElseThrow(() -> new ArtistNotFoundException(id));
    }

    public void delete(Artist artist) {
        if (repository.existsById(artist.getId()))
        repository.delete(artist);
    }

    public void deleteById(Long id) {

        if (repository.existsById(id))
            repository.deleteById(id);
    }

}
