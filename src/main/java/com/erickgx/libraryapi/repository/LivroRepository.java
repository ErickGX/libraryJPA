package com.erickgx.libraryapi.repository;

import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query methods : https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);
//
//    //caso pudesse retornar nada , caso campo unico
//    Optional<Livro> findByIsbn(String isbn);
//
//    // select * from livro where titulo like = "%titulo"
//    List<Livro> findByTituloEndingWith(String titulo);
//
//    //select * from livro where titulo like = "titulo%"
//    List<Livro> findByTituloStartingWith(String titulo);
//
//    //select * from livro where titulo like = "%titulo%"
//    List<Livro> findByTituloContaining(String titulo);
//
//    // select * from livro where titulo =? and preco = ?
//    List<Livro> findByTituloANDPreco(String titulo, BigDecimal preco);
//
//    //select * from livro where isbn =? or titulo = ?
//    List<Livro> findByIsbnORTitulo(String isbn, String titulo);
//
//
//    List<Livro> findByPrecoGreaterThan(BigDecimal preco);

}
