package com.erickgx.libraryapi.controller.dto;

import com.erickgx.libraryapi.models.Autor;

import java.time.LocalDate;
import java.util.UUID;


public record AutorDTO(
        UUID id,
        String nome,
        LocalDate dataNascimento,
        String nacionalidade
)  {

    //mapeamento do metodo antigo manual
    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }

}

