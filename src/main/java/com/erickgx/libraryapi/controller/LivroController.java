package com.erickgx.libraryapi.controller;

import com.erickgx.libraryapi.controller.dto.CadastroLivroDTO;
import com.erickgx.libraryapi.controller.dto.ErroResposta;
import com.erickgx.libraryapi.exceptions.RegistroDuplicadoException;
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
public class LivroController {

    private final LivroService service;

    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody @Valid CadastroLivroDTO dto){
            //mapear dto para entidade
            //enviar a entidade para o service validar e salvar na base
            // criar url para acesso dos dados do livro
            // retornar codigo created com header location
        try {
            return ResponseEntity.ok(dto);

        }catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }

    }
}
