package com.erickgx.libraryapi.repository;


import com.erickgx.libraryapi.models.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {


    @Autowired
    AutorRepository autorRepository;

    @Test
    public void saveTest(){
        Autor autor = new Autor();
        autor.setNome("Erick");
        autor.setDataNascimento(LocalDate.of(1999, 4, 23));
        autor.setNacionalidade("Brasileira");

        var autorSalvo =  autorRepository.save(autor);
        System.out.println("Autor Salvo : " + autorSalvo);
    }



    @Test
    public void updateAutor(){
        Optional<Autor> possivelAutor =  autorRepository.findById(UUID.fromString("0e78deaf-9aac-430b-bcc4-dc830e67363e"));

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Dados do autor : ");
            System.out.println(autorEncontrado);


            autorEncontrado.setDataNascimento(LocalDate.of(2002, 6 , 04));
            autorRepository.save(autorEncontrado);
        }
    }

    @Test
    public void listAutors(){

        List<Autor> autores =  autorRepository.findAll();

        System.out.println(autores);
    }
}
