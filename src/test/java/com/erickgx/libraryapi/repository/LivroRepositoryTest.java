package com.erickgx.libraryapi.repository;

import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.models.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    public void saveTest(){
        Livro livro = new Livro();
        livro.setIsbn("93123-5312");
        livro.setPreco(BigDecimal.valueOf(99));
        livro.setTitulo("Biografia do king bengala");
        livro.setDataPublicacao(LocalDate.of(2005, 2, 27));
        livro.setGenero(Genero.FANTASIA);

        var id = UUID.fromString("abf41f24-e385-4f8b-b7df-1f9e3b105af0");
        Autor autor = autorRepository.
                findById(id)
                .orElse(null);

        livro.setAutor(autor);
         livroRepository.save(livro);


    }

    @Test
    public void saveCascadeLivroTest(){

        Livro livro = new Livro();
        livro.setIsbn("12323-5312");
        livro.setPreco(BigDecimal.valueOf(159));
        livro.setTitulo("Black Clover");
        livro.setDataPublicacao(LocalDate.of(2025, 5, 30));
        livro.setGenero(Genero.CIENCIA);


        Autor autor = new Autor();
        autor.setNome("Tralalero Tralala");
        autor.setDataNascimento(LocalDate.of(1990, 10, 12));
        autor.setNacionalidade("Brainrot");

        livro.setAutor(autor);

        livroRepository.save(livro);


    }

    @Test
    public void listLivros(){

        List<Livro> livros =  livroRepository.findAll();

        livros.forEach(System.out::println); //method reference
    }

    @Test
    @Transactional //usei por causa do lazy na classe , ele executa duas consultas idenpendentes , fetch strategy
    void buscarLivro() {
        var id = UUID.fromString("38389c1c-5f2a-48bb-a442-ea54e72e714c");
        Livro livro = livroRepository.findById(id).orElse(null);
        System.out.println("Livro :");
        System.out.println(livro.getTitulo());

        System.out.println("Autor :");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void acharPorIsbn(){
        Optional<Livro> livro = livroRepository.findByIsbn("92930-2312");
        //sem tratamento pq o é um id existente para Teste

        System.out.println(livro);
    }

    @Test
    void acharPorTituloLikeFinal(){
        List<Livro> livro = livroRepository.findByTituloEndingWith("colorido");

        livro.forEach(System.out::println);
    }

    @Test
    void acharPorTituloLikeComeco(){
        List<Livro> livro = livroRepository.findByTituloStartingWith("50");
        livro.forEach(System.out::println);
    }

    @Test
    void acharPorTituloLikeMeio(){
        List<Livro> livro = livroRepository.findByTituloContaining("jailson");
        livro.forEach(System.out::println);
    }

    @Test
    void buscarPorTituloAndPreco(){
        List<Livro> livro = livroRepository.findByTituloAndPreco("Omnicient reader view point", BigDecimal.valueOf(159));
        livro.forEach(System.out::println);
    }

    @Test
    void buscarPorIsbnOrTitulo(){
        List<Livro> livro = livroRepository.findByIsbnOrTitulo("2222-5312", "Solo max newbie");
        livro.forEach(System.out::println);
    }

    @Test
    void  buscarPrecoMaiorQue(){
        List<Livro> livro = livroRepository.findByPrecoGreaterThan(BigDecimal.valueOf(99));
        livro.forEach(System.out::println);
    }

    @Test
    void buscarEntreDataPublicacao(){
        List<Livro> livro = livroRepository.findByDataPublicacaoBetween(
                LocalDate.of(1992, 1 ,1),
                LocalDate.of(2006, 10, 10));
        livro.forEach(System.out::println);
    }

    @Test
    void buscarPorTituloOrdenarPorDataPublicacaoAscendente(){
        List<Livro> livro = livroRepository.findByTituloOrderByDataPublicacaoAsc("Biografia do jailson mendes");
        livro.forEach(System.out::println);
    }

    @Test
    void buscarPorTituloIgnoreCase(){
        List<Livro> livro = livroRepository.findByTituloIgnoreCase("bOoK oF traNsAcioNAL");
        livro.forEach(System.out::println);
    }




    @Test
    void listarLivrosComQueryJPQL(){
        var resultado = livroRepository.listarTodosOrdenadosPorTituloAndPreco();

        resultado.forEach(System.out::println);
    }

    @Test
    void listarAutoresDosLivros(){
        var autores = livroRepository.listarAutoresDosLivros();

        autores.forEach(System.out::println);

    }
    @Test
    void listarTitulosNaoRepetidosLivros(){
        var livros = livroRepository.listarTitulosDiferentesLivros();

        livros.forEach(System.out::println);
    }


    @Test
    void listarPorGeneroQueryParamTest(){
        var livros = livroRepository.findByGenero(Genero.FANTASIA, "preco");
        livros.forEach(System.out::println);
    }

    @Test
    void listarPorGeneroPositionalParameters(){
        var livros = livroRepository.findByGeneroPositionalParameters("preco", Genero.CIENCIA);
        livros.forEach(System.out::println);
    }
}