package com.erickgx.libraryapi.repository;

import com.erickgx.libraryapi.models.Autor;
import com.erickgx.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacoesTest {

    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit -> confirma alterações
     * rollback -> desfazer alterações
     * Uteis em uma operação que envolve varias manipulações no banco de dados que devem ser ATOMICAS
     *  Garante Atomicidade de uma transação (Executa todos os passos certos ou Desfaz tudo)
     *  Não é possivel fazer transacao dentro de um test , apenas em uma classe
     */
    @Test
    void transacaoTeste(){
        transacaoService.executar();
    }

    @Test
    void transacaoEstadoManaged(){
        transacaoService.atualizacaoSemChamarSave();
    }

}
