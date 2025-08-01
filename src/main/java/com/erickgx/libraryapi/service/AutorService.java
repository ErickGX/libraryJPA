package com.erickgx.libraryapi.service;

import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public Autor salvar (Autor autor){
        return repository.save(autor);
    }
}
