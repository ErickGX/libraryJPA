package com.erickgx.libraryapi.models;

import com.erickgx.libraryapi.enums.Genero;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "livro")
@EntityListeners(AuditingEntityListener.class)
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false, unique = true)
    private String isbn;

    @Column(length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private Genero genero;

    @Column(precision = 18, scale = 2)
    private BigDecimal preco;

    @CreatedDate //Spring preenche a data automaticamente ao criar
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate //Spring preenche a data automaticamente ao atualizar
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(name = "id_usuario")
    private UUID idUsuario;

    //não recomendado o uso de cascade tipe
    @ManyToOne//(fetch = FetchType.LAZY)//lazy tras apenas o objeto da classe , nao é possivel acessar entidades associadas
    // manyToOne por padrao é eager , ele tras junto o autor com join no bd
    // //cascade all faz com que eu consiga salvar um livro apenas setando o autor com new, sem ter persistido antes
    @JoinColumn(name = "id_autor", nullable = false)
    private Autor autor;
}
