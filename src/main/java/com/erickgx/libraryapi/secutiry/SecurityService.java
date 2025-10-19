package com.erickgx.libraryapi.secutiry;

import com.erickgx.libraryapi.models.Usuario;
import com.erickgx.libraryapi.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;


        public Usuario getUsuarioLogado() {
            //Dentro do contexto de segurança, obter a autenticação atual
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            //usando o customAuthentication para retornar o usuario logado
            if (authentication instanceof CustomAuthentication customAuth){
                return customAuth.getUsuario();
            }

            return null;
        }
}
