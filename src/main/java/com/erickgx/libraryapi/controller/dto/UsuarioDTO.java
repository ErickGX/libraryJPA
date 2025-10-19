package com.erickgx.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank(message = "Campo Login Obrigatorio")
        String login,
        @NotBlank(message = "Campo Senha Obrigatorio")
        @Email(message = "Inválido")
        String email,
        @NotBlank(message = "Campo Senha Obrigatorio")
        String senha,
        List<String> roles
) {
}
