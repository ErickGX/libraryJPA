package com.erickgx.libraryapi.repository.specs;

import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Livro;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    //movi a criacao do isbnEqual da service para a classe Specs Livro , e so utilizo o metodo
    //Pesquisa com Equal/ findByIsbn
    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Livro> tituloLike(String titulo) {
                // upper(livro.titulo) like (%:param) // diferente do Example que tem o Mathcer Containing (Like)
        return (root, query, cb) ->  // aqui é feito na mao o like
                cb.like(cb.upper(root.get("titulo")), "%" + titulo .toUpperCase() + "%");
    }
    //os campos dentro do get sao baseados nas entidades
    public static Specification<Livro> generoEqual(Genero genero) {
        return (root, query, cb) ->
                cb.equal(root.get("genero"), genero);
    }

    //to_char(data_publicacao, 'DD/MM/YYYY') = :anoPublicacao
    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal( cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")),anoPublicacao.toString());
    }



}
