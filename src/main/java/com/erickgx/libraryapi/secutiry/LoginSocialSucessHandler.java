package com.erickgx.libraryapi.secutiry;

import com.erickgx.libraryapi.models.Usuario;
import com.erickgx.libraryapi.service.UsuarioService;
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
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioService usuarioService;
    private static final String SENHA_PADRAO = "123456"; //senha padrao para usuarios via social login

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

        if (usuario == null) {
            usuario = CadastrarNovoUsuarioViaGoogle(email);
        }

        //conversao para a nossa autenticacao customizada , conversao do userGoogle para o Usuario do sistema
        CustomAuthentication customAuthentication =  new CustomAuthentication(usuario);

        //seto o contexto de seguranca com a autenticacao customizada
        SecurityContextHolder.getContext().setAuthentication(customAuthentication);

        //chamo o metodo da superclasse para continuar o fluxo normal com a autenticacao customizada
        super.onAuthenticationSuccess(request, response, customAuthentication);
    }

    private Usuario CadastrarNovoUsuarioViaGoogle(String email) {
        Usuario usuario;
        usuario =  new Usuario();
        usuario.setEmail(email);
        usuario.setLogin(obterLoginApartirEmail(email)); //capturando o login a partir do email
        usuario.setSenha(SENHA_PADRAO); //definindo uma senha padrao para usuarios via social login
        usuario.setRoles(List.of("OPERADOR"));

        usuarioService.salvar(usuario);
        return usuario;
    }

    private String obterLoginApartirEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    //esta versao recebe o filterchain como parametro
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
//        super.onAuthenticationSuccess(request, response, chain, authentication);
//    }
}
