package com.erickgx.libraryapi.service;


import com.erickgx.libraryapi.enums.Genero;
import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.models.Livro;
import com.erickgx.libraryapi.repository.AutorRepository;
import com.erickgx.libraryapi.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {


  private final  AutorRepository autorRepository;

  private final  LivroRepository livroRepository;

    //livro (titulo, ... , nome_arquivo) -> id.png
    @Transactional
    public void salvarLivroComFoto(){
        //salvar o livro
        //livroRepository.save(livro);

        //pega o id do livro = livro.getId();
        //var id = livro.getId();

        //salvar foto do livro -> bucket na Nuvem (armazenamento de fotos , videos etc na nuvem)
        //bucketService.salvar(livro.getFoto(), id + ".png");

        //atualizar o nome do arquivo que não tinha sido setado na primeira l
        // livro.setNomeArquivoFoto(id + ".png");

        //Esse processo ocorrendo dentro de uma transação
        //Faz que ao eu possa salvar o livro , e atualizar ele no final da transação sem usar save novamente
        //ao final da transacao o commit detecta a entidade que esta managed sofreu alteração
        // e envia a alteração automaticamente para o banco
        //Qualquer alteração em uma entidade managed é refletida ao final da transação
    }

    @Transactional
    public void atualizacaoSemChamarSave() {
        // Busca a entidade, agora em estado "Managed" pois está dentro da transação
        var livro = livroRepository
                .findById(UUID.fromString("8e464095-a797-48ea-82f9-b076586954dd"))
                .orElse(null);

        if (livro == null) {
            throw new RuntimeException("Livro não encontrado.");
        }

        // Modificação do campo — JPA rastreia isso porque o objeto está no estado Managed
        livro.setDataPublicacao(LocalDate.of(2025, 12, 12));

        // Nenhum uso de save() -> no final da transação, o EntityManager faz o flush automático para o banco
        //ao acontecer o commit no final da transação bem sucedida
    }

    @Transactional
    public void executar(){

        Autor autor = new Autor();
        autor.setNome("teste Transação");
        autor.setDataNascimento(LocalDate.of(1990, 10, 12));
        autor.setNacionalidade("Transacionalista");

        autorRepository.save(autor);

        Livro livro = new Livro();
        livro.setIsbn("09874-5323");
        livro.setPreco(BigDecimal.valueOf(2259));
        livro.setTitulo("Teste Book of Transacional");
        livro.setDataPublicacao(LocalDate.of(2025, 5, 30));
        livro.setGenero(Genero.CIENCIA);

        livro.setAutor(autor);

        livroRepository.save(livro); //saveAndFlush ele envia o script pro banco de dados porem é possivel ter rollback no final
        //metodo save dentro de uma transacao só envia os scripts apos ter passado
        //todos os passos sem erro , saveAndFlush apenas sé for realmente necessario


        if (autor.getNome().equals("Qualquer erro")){
            throw  new RuntimeException("Rollback!");
        }

    }
}
