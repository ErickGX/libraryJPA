package com.erickgx.libraryapi.controller.dto;

import com.erickgx.libraryapi.enums.Genero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultadoPesquisaLivroDTO(
        UUID idLivro,
        String isbn,
        String titulo,
        LocalDate dataPublicacao,
        Genero genero,
        BigDecimal preco,
        AutorDTO autor //troca de informacoes entre DTO para nao expor a entidade concreta
        ) {
}
