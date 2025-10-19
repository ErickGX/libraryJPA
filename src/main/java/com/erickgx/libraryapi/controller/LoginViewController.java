package com.erickgx.libraryapi.controller;


import com.erickgx.libraryapi.secutiry.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //@Controller espera uma pagina de retorno e @RestController espera um json
public class LoginViewController {


    @GetMapping("/login")
    public String pageLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        if (authentication instanceof CustomAuthentication customAuth){
            System.out.println(customAuth.getUsuario());
        }
        return "Olá, " + authentication.getName();
    }
}
