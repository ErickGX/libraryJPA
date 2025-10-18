package com.erickgx.libraryapi.secutiry;

import com.erickgx.libraryapi.models.Usuario;
import com.erickgx.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;


        public Usuario getUsuarioLogado() {
            //Dentro do contexto de segurança, obter a autenticação atual
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            //capturar o login do usuário logado
            String login = userDetails.getUsername();

            return usuarioService.obterPorLogin(login);
        }
}
