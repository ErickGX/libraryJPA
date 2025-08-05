package com.erickgx.libraryapi.controller;


import com.erickgx.libraryapi.controller.dto.AutorDTO;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptional = service.obterPorId(idAutor);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    //parametros com required false sao opcionais, todos sao true por padrao ao menos que mude para false
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        List<Autor> resultados = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> LISTA = resultados.
                stream()
                .map(autor -> new AutorDTO(
                    autor.getId(),
                    autor.getNome(), autor.getDataNascimento(),
                    autor.getNacionalidade())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(LISTA);

    }
}
