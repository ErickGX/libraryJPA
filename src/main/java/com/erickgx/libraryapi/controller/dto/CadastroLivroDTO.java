package com.erickgx.libraryapi.controller.dto;

import com.erickgx.libraryapi.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CadastroLivroDTO(


        String id,

        @ISBN
        @NotBlank(message = "Campo Obrigatorio")
        String isbn,

        @NotBlank(message = "Campo Obrigatorio")
        String titulo,

        @NotNull(message = "Campo Obrigatorio")
        @Past(message = "Não permitido Data Futura")
        LocalDate dataPublicacao,

        Genero genero,
        BigDecimal preco,

        @NotNull(message = "Campo Obrigatorio")
        UUID idAutor
        )
    {
}
