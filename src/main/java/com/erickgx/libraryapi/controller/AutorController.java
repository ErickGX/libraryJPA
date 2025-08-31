package com.erickgx.libraryapi.controller;


import com.erickgx.libraryapi.controller.dto.AutorDTO;
import com.erickgx.libraryapi.controller.dto.ErroResposta;
import com.erickgx.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.erickgx.libraryapi.exceptions.RegistroDuplicadoException;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.service.AutorService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor //anotação que substitui o construtor feito manualmente com D.I / private final
public class AutorController {

    private final AutorService service;


    @PostMapping
    public ResponseEntity<Object> salvar (@RequestBody AutorDTO autor){
       try {
           var autorEntidade = autor.mapearParaAutor();
           service.salvar(autorEntidade);

           URI localion = ServletUriComponentsBuilder
                   .fromCurrentRequest()
                   .path("/{id}")
                   .buildAndExpand(autorEntidade.getId())
                   .toUri();

           //acesso para o recurso criado
           return ResponseEntity.created(localion).build();
       }catch (RegistroDuplicadoException e){
           var erroDTO = ErroResposta.conflito(e.getMessage());
           return ResponseEntity.status(erroDTO.status()).body(erroDTO);
       }


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
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            service.deletar(autorOptional.get());
            return ResponseEntity.noContent().build();
        } catch (OperacaoNaoPermitidaException e){
            var erroResposta = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroResposta.status()).body(erroResposta);
        }
    }

    //parametros com required false sao opcionais, todos sao true por padrao ao menos que mude para false
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade){

        List<Autor> resultados = service.pesquisa(nome, nacionalidade);
        List<AutorDTO> LISTA = resultados.stream().
                map(autor -> new AutorDTO(
                        autor.getId(),
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade())
                ).collect(Collectors.toList());

        return ResponseEntity.ok(LISTA);

    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @PathVariable("id") String id,  @RequestBody AutorDTO dto){

        try {
            var idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = service.obterPorId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e){
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }

    }
}
