package com.erickgx.libraryapi.controller;

import com.erickgx.libraryapi.controller.dto.CadastroLivroDTO;
import com.erickgx.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.erickgx.libraryapi.controller.mappers.LivroMapper;
import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.service.LivroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private final LivroService service;
    private final LivroMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid CadastroLivroDTO dto) {
        Livro livro = mapper.toEntity(dto);
        service.salvar(livro);
        var url = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> obterDetalhes(@PathVariable String id) {
        return service.obterPorId(UUID.fromString(id))
                .map(livro -> {
                    var dto = mapper.toDTO(livro);
                    return ResponseEntity.ok(dto);
                }).orElseGet( () -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id) {
        return service.obterPorId(UUID.fromString(id)) //se encontrado gera um livro
                .map(livro -> { // objeto obtido usado para o delete
                    service.deletar(livro);
                            return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisar(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            Genero genero ,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao
    ) {
        var resultado = service.pesquisar(isbn, titulo, nomeAutor, genero, anoPublicacao);
         var lista =  resultado
                 .stream()
                 .map(mapper::toDTO)
                 .collect(Collectors.toList());

         return ResponseEntity.ok(lista);
    }


}
