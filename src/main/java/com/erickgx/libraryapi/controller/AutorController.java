package com.erickgx.libraryapi.controller;


import com.erickgx.libraryapi.controller.dto.AutorDTO;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.service.AutorService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar (@RequestBody AutorDTO autor){
        var autorEntidade = autor.mapearParaAutor();
        service.salvar(autorEntidade);

        URI localion = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        //acesso para o recurso criado
        return ResponseEntity.created(localion).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
         Optional<Autor> autorOptional = service.obterPorId(idAutor);
         if (autorOptional.isPresent()){
             Autor autor = autorOptional.get();
             AutorDTO dto = new AutorDTO(
                     autor.getId(),
                     autor.getNome(),
                     autor.getDataNascimento(),
                     autor.getNacionalidade());
             return ResponseEntity.ok(dto);
         }

         return ResponseEntity.notFound().build();


    }
}
