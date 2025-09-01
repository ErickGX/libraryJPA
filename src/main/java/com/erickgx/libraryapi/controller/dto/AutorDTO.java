package com.erickgx.libraryapi.controller.dto;

import com.erickgx.libraryapi.models.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;


public record AutorDTO(
        UUID id,

        //@CPF @CNPJ Anotacoes para documentos brasileiros muito util, olhar documentacoes de classe internas
        //varias anotacoes uteis no pacote
        @NotBlank(message = "Campo nome Obrigatorio")
        @Size(max = 100 , min = 3 , message = "Campo fora do tamanho permitido")
        String nome,

        @NotNull(message = "Campo nascimento Obrigatorio")
        @Past(message = "Não pode ser uma data maior que a atual")
        LocalDate dataNascimento,

        @NotBlank(message = "Campo nacionalidade obrigatorio")
        @Size(max = 50 , min = 3 , message = "Campo fora do tamanho permitido")
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

