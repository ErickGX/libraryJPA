package com.erickgx.libraryapi.service;

import com.erickgx.libraryapi.exceptions.OperacaoNaoPermitidaException;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.repository.AutorRepository;
import com.erickgx.libraryapi.repository.LivroRepository;
import com.erickgx.libraryapi.validator.AutorValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor //anotação que substituie o construtor manual com a D.I
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator validator;
    private final LivroRepository livroRepository;



    public Autor salvar (Autor autor){
         validator.validar(autor);
        return repository.save(autor);
    }


    public Optional<Autor> obterPorId(UUID id){
        return repository.findById(id);
    }

    public void deletar(Autor autor){
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException(
                    "Não é permitido excluir um Autor que possui livros cadastrados!");
        }
        repository.delete(autor);
    }

    //metodo mais complexo e verboso , substituido pelo pesquisa ByExample
    public List<Autor> pesquisa(String nome, String nacionalidade){
        if(nome != null & nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        }

        if (nome != null){
            return repository.findByNome(nome);
        }

        if (nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }

        return repository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome , String nacionalidade){
        var autor =  new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher
                .matching()    //Muito util para pesquisa dinamicas
                //.withIgnorePaths("id", "dataNascimento", "dataCadastro") usado para ignorar campos caso receba uma entidade inteira como parametro
                .withIgnoreNullValues() //ignora campos da entidade que nao foram mandados para a pesquisa
                .withIgnoreCase() // ignora maisculas e minusculas
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
                //StringMatcher.Containing faz a pesquisa %any% , se a palavra contem em qualquer parte da string
                // seja inicio meio ou fim

        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }


    public void atualizar(Autor autor){
        if (autor.getId() == null){
            throw new IllegalArgumentException("Para atualizar é necessario que o autor já esteja salvo na base");
        }
        validator.validar(autor);
        repository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
