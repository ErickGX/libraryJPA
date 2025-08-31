package com.erickgx.libraryapi.validator;

import com.erickgx.libraryapi.exceptions.RegistroDuplicadoException;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AutorValidator {

    private final AutorRepository repository;


    public void validar(Autor autor){
            if (existeAutorCadastrado(autor)){
                throw  new RegistroDuplicadoException("Autor já Cadastrado");
            }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado =  repository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade()
        );

        // Se o autor não tem ID, é um novo cadastro
        // Se já existir um autor com os mesmos nome, data de nascimento e nacionalidade,
        // então há duplicação → retorna true para bloquear
        if (autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        // Se o autor tem ID, é uma atualização
        // Queremos verificar se os dados (nome, data, nacionalidade) pertencem a outro autor diferente
        // Se encontrou um autor, mas o ID é diferente do que está sendo editado → conflito de duplicação
        // Se encontrou um autor, mas é o mesmo (mesmo ID) → é só edição, não é duplicação
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();

        //outra forma de se fazer o codigo
       /* return autorEncontrado
                .map(a -> !a.getId().equals(autor.getId()))  // é outro autor?
                .orElse(false);                              // se não encontrou, não é duplicado */
    }


}
