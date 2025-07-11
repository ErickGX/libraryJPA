package com.erickgx.libraryapi.repository;


import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.models.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {


    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void saveTest(){
        Autor autor = new Autor();
        autor.setNome("Kekson");
        autor.setDataNascimento(LocalDate.of(1994, 10, 20));
        autor.setNacionalidade("keklandia");

        var autorSalvo =  autorRepository.save(autor);
        System.out.println("Autor Salvo : " + autorSalvo);
    }



    @Test
    public void updateAutor(){
        Optional<Autor> possivelAutor =  autorRepository.findById(UUID.fromString("36612de7-1056-48e6-b63c-8a1c77aa60b7"));

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor : ");
            System.out.println(autorEncontrado);


            autorEncontrado.setDataNascimento(LocalDate.of(2002, 6 , 04));
            autorRepository.save(autorEncontrado);
        }else{
            System.out.println("Autor não encontrado");
        }
    }

    @Test
    public void listAutors(){

        List<Autor> autores =  autorRepository.findAll();

         autores.forEach(System.out::println); //method reference
    }

    @Test
    public void countAutor(){
        System.out.println("Contagem de autores : " + autorRepository.count());
    }

    @Test
    public void deletarAutorID(){

         var id  = UUID.fromString("8c8ef623-3d5d-43dc-8bbd-b3f79be1bd29");
        boolean existente =  autorRepository.existsById(id);//procura se um id existe no banco de dados retorna bool

        if(existente){
            autorRepository.deleteById(id); //deleta pelo campo ID
            System.out.println("Autor deletado com sucesso");
        }else {
            System.out.println("Não existe esse infeliz");
        }

    }

    @Test
    public void deletarAutorByEntity(){ //metodo recebe uma entidadae
        var id = UUID.fromString("92370484-d365-4acd-b019-f51d3b1aa435");

        var autor  =  autorRepository.findById(id).get(); //metodo recebe uma entidada da busca

        autorRepository.delete(autor); //delete por entidade
    }

    @Test
    void SalvarAutorComLivrosTest(){

        Autor autor1 = new Autor();
        autor1.setNome("Cristal");
        autor1.setDataNascimento(LocalDate.of(2002, 9, 2));
        autor1.setNacionalidade("França");



        Livro livro = new Livro();
        livro.setIsbn("1111-5312");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setTitulo("Solo max newbie");
        livro.setDataPublicacao(LocalDate.of(1992, 5, 20));
        livro.setGenero(Genero.MISTERIO);
        livro.setAutor(autor1);

        Livro livro2 = new Livro();
        livro2.setIsbn("2222-5312");
        livro2.setPreco(BigDecimal.valueOf(59));
        livro2.setTitulo("50 tons de colorido");
        livro2.setDataPublicacao(LocalDate.of(2005, 2, 10));
        livro2.setGenero(Genero.CIENCIA);
        livro2.setAutor(autor1);

        Livro livro3 = new Livro();
        livro3.setIsbn("33333-5312");
        livro3.setPreco(BigDecimal.valueOf(19));
        livro3.setTitulo("John wick matando palavras");
        livro3.setDataPublicacao(LocalDate.of(2006, 9, 10));
        livro3.setGenero(Genero.FICCAO);
        livro3.setAutor(autor1);

        Livro livro4 = new Livro();
        livro4.setIsbn("44444-5312");
        livro4.setPreco(BigDecimal.valueOf(9));
        livro4.setTitulo("Biografia do jailson mendes");
        livro4.setDataPublicacao(LocalDate.of(2024, 12, 9));
        livro4.setGenero(Genero.BIOGRAFIA);
        livro4.setAutor(autor1);

        autor1.setLivros(new ArrayList<>());
        autor1.getLivros().add(livro);
        autor1.getLivros().add(livro2);
        autor1.getLivros().add(livro3);
        autor1.getLivros().add(livro4);

        autorRepository.save(autor1);


        //posso usar com cascade no autor e nao usar o saveAll de livro , apenas salvar o autor usando
        livroRepository.saveAll(autor1.getLivros());//salvando varios livros pra 1 autor
        // no front pode ser um formulario que cadastro 1 autor e varios livros de uma vez usando List


    }


    @Test
    void listarLivrosAutor(){
        var id = UUID.fromString("abf41f24-e385-4f8b-b7df-1f9e3b105af0");
        var autor = autorRepository.findById(id).get(); //recuperei a classe toda com .get()

        List<Livro> listaLivros =  livroRepository.findByAutor(autor);
        autor.setLivros(listaLivros);

        autor.getLivros().forEach(System.out::println);

    }
}
