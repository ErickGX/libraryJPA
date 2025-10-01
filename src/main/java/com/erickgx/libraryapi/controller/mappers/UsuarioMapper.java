package com.erickgx.libraryapi.controller.mappers;


import com.erickgx.libraryapi.controller.dto.UsuarioDTO;
import com.erickgx.libraryapi.models.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDTO(Usuario entity);
}
