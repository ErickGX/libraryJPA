package com.erickgx.libraryapi.controller.mappers;

import com.erickgx.libraryapi.controller.dto.CadastroLivroDTO;
import com.erickgx.libraryapi.controller.dto.ResultadoPesquisaLivroDTO;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Mapper(componentModel = "spring" , uses = AutorMapper.class)
public abstract class LivroMapper {
    //MapStruc permite o uso de classes abstratas tambem

    @Autowired
    AutorRepository autorRepository;

    //dá um nome para o metodo auxilar , e reutilizo para montar a entidade completa
    @Named("mapAutorById")
    protected Autor mapAutorById(UUID idAutor) {
        return autorRepository.findById(idAutor).orElse(null);
    }

    //@Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    @Mapping(target = "autor", source = "dto.idAutor", qualifiedByName = "mapAutorById")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
