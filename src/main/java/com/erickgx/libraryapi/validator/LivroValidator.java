package com.erickgx.libraryapi.validator;

import com.erickgx.libraryapi.exceptions.CampoInvalidoException;
import com.erickgx.libraryapi.exceptions.RegistroDuplicadoException;
import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private static final int ANO_EXIGENCIA_PRECO = 2020;

    private final LivroRepository repository;

    public void validar(Livro livro){
        if (bookWithIsbnExists(livro)){
            throw new RegistroDuplicadoException("ISBN já cadastrado");
        }

        if (isPrecoObrigatorioNulo(livro)){
            throw new CampoInvalidoException("preco", "Para livros com ano de publicacao a partir de 2020 , preco é obrigatorio");
        }
    }

    private boolean isPrecoObrigatorioNulo(Livro livro) {
        return livro.getPreco() == null &&
                livro.getDataPublicacao().getYear() >= ANO_EXIGENCIA_PRECO;
    }

    //verifica se existe registro duplicado com msm ISBN
    // compara se o id do livroEncontrado é diferente do id do livro passado
    //cria exception e cai no metodo validar acima
    private Boolean bookWithIsbnExists(Livro livro){

        //verifica se existe livro já cadastrado com o ISBN passado por parametro
        Optional<Livro> livroEncontrado = repository.findByIsbn(livro.getIsbn());


        //verifica se o livro passado tem o msm id do livroEncontrado
        // ai entra registro duplicado
        if (livro.getId() == null){
            return livroEncontrado.isPresent();
        }

        //verifica se o livroEncontrado não possui o mesmo id do livro passado como parametro
        return livroEncontrado
                .map(Livro::getId)
                .stream()
                .anyMatch(id -> !id.equals(livro.getId()));
    }
}
