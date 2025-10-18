package com.erickgx.libraryapi.service;

import com.erickgx.libraryapi.models.Usuario;
import com.erickgx.libraryapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public void salvar(Usuario usuario) {
        String senha = usuario.getSenha();
        usuario.setSenha(encoder.encode(senha)); //Bcrypt para criptografar a senha
        repository.save(usuario);
    }

    public Usuario obterPorLogin(String login) {
        return repository.findByLogin(login);
    }
}
