package com.erickgx.libraryapi.secutiry;

import com.erickgx.libraryapi.models.Usuario;
import com.erickgx.libraryapi.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {


        //Aqui podemos pegar os dados do usuario autenticado via OAuth2
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        //Pegando os dados do usuario autenticado
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        //capturo o atributo email do usuario autenticado
        String email =  oAuth2User.getAttribute("email");

        //chamo a service para buscar por um usuario com este email fornecido pelo googleUser
        Usuario usuario = usuarioService.obterPorEmail(email);

        //conversao para a nossa autenticacao customizada , conversao do userGoogle para o Usuario do sistema
        CustomAuthentication customAuthentication =  new CustomAuthentication(usuario);

        //seto o contexto de seguranca com a autenticacao customizada
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        //chamo o metodo da superclasse para continuar o fluxo normal com a autenticacao customizada
        super.onAuthenticationSuccess(request, response, customAuthentication);
    }

    //esta versao recebe o filterchain como parametro
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        super.onAuthenticationSuccess(request, response, chain, authentication);
//    }
}
