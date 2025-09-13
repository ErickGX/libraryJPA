package com.erickgx.libraryapi.service;

import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;


    public Livro salvar(Livro livro) {
       return repository.save(livro);

    }
}
