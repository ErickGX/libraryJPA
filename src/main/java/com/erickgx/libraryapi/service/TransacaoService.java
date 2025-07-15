package com.erickgx.libraryapi.service;


import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.repository.AutorRepository;
import com.erickgx.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {

    @Autowired
    AutorRepository autorRepository;

    @Autowired
    LivroRepository livroRepository;

    @Transactional
    public void executar(){

        Autor autor = new Autor();
        autor.setNome("teste Transação");
        autor.setDataNascimento(LocalDate.of(1990, 10, 12));
        autor.setNacionalidade("Transacionalista");

        autorRepository.saveAndFlush(autor);

        Livro livro = new Livro();
        livro.setIsbn("09874-5323");
        livro.setPreco(BigDecimal.valueOf(2259));
        livro.setTitulo("Teste Book of Transacional");
        livro.setDataPublicacao(LocalDate.of(2025, 5, 30));
        livro.setGenero(Genero.CIENCIA);

        livro.setAutor(autor);

        livroRepository.saveAndFlush(livro); //saveAndFlush ele envia o script pro banco de dados porem é possivel ter rollback no final
        //metodo save dentro de uma transacao só envia os scripts apos ter passado
        //todos os passos sem erro , saveAndFlush apenas sé for realmente necessario


        if (autor.getNome().equals("teste Transação")){
            throw  new RuntimeException("Rollback!");
        }

    }
}
