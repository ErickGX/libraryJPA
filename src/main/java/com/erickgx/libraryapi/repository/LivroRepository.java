package com.erickgx.libraryapi.repository;

import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    // Query methods : https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html
    // select * from livro where id_autor = id
    List<Livro> findByAutor(Autor autor);

    //caso pudesse retornar nada , caso campo unico
    Optional<Livro> findByIsbn(String isbn);

    // select * from livro where titulo like = "%titulo"
    List<Livro> findByTituloEndingWith(String titulo);

    //select * from livro where titulo like = "titulo%"
    List<Livro> findByTituloStartingWith(String titulo);

    //select * from livro where titulo like = "%titulo%"
    List<Livro> findByTituloContaining(String titulo);

    // select * from livro where titulo =? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where isbn =? or titulo = ?
    List<Livro> findByIsbnOrTitulo(String isbn, String titulo);

    
    List<Livro> findByPrecoGreaterThan(BigDecimal preco);

    List<Livro> findByDataPublicacaoBetween(LocalDate inicio , LocalDate fim);

    List<Livro> findByTituloOrderByDataPublicacao(String titulo);

    List<Livro> findByTituloIgnoreCase(String titulo);

    //Consultas com JPQL -> REFERENCIAS AS ENTIDADES E AS PROPRIEDADES
    // select l.* from livro as l order by l.titulo
    @Query(" select l from Livro as l order by l.titulo, l.preco ")
    List<Livro> listarTodosOrdenadosPorTituloAndPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    //O query usando as propriedades da classe é diferente da feita diretamente no banco
    // O join é feito usando o l.autor da classe que já apoonta para a chave na classe autor diretamente
    @Query(" select a from Livro l join l.autor a ")
    List<Autor> listarAutoresDosLivros();


//    //select distinct l.* from livro l;
//    @Query(" select distinct l.titulo from Livro l")
//    List<String> listarTitulosDiferentesLivros();
//
//    @Query("""
//            select l.genero
//            from livro l
//            join l.autor a
//            where a.nacionalidade = 'Brasileira'
//            order by l.genero
//            """)
//    List<String> listarGenerosAutoresBrasileiros();
//
//    @Query(" select l from livro l  where l.genero = :genero order by :paramOrdenacao ")
//    List<Livro> findByGenero(
//            @Param("genero") Genero generoLivro,
//            @Param("paramOrdenacao") String nomePropriedade
//    );




}
