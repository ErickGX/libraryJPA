package com.erickgx.libraryapi.controller;

import com.erickgx.libraryapi.controller.dto.CadastroLivroDTO;
import com.erickgx.libraryapi.controller.mappers.LivroMapper;
import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {


        //mapear dto para entidade
        //enviar a entidade para o service validar e salvar na base
        // criar url para acesso dos dados do livro
        // retornar codigo created com header location
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);

        var url = gerarHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();


    }
}
