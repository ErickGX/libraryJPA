package com.erickgx.libraryapi.controller.mappers;

import com.erickgx.libraryapi.controller.dto.AutorDTO;
import com.erickgx.libraryapi.models.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//transforma em um componente spring em tempo de compilação
@Mapper(componentModel = "spring")
public interface AutorMapper {

    //Quando o DTO e a Model tem os mss campos com nomes diferentes
    //é possivel fazer o mapeamento do campo da DTO para a model e vice versa
    @Mapping(source = "nome", target = "nome")
    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);
}
