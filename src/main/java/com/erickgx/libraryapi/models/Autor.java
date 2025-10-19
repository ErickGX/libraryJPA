package com.erickgx.libraryapi.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "autor", schema = "public")
@ToString(exclude = {"livros"})
@EntityListeners(AuditingEntityListener.class)
//config necessaria para criar um listener para os @CreatedDate e  @LastModifiedDate
//ativar config @EnableJpaAuditing na classe de configuração ex : Application.Java ou outra
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    // @Column(columnDefinition = "BINARY(16)") //HABILITAR QUANDO TIVER USANDO MYSQL E TIRAR QUANDO USAR POSTGRESS
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    @CreatedDate //Spring preenche a data automaticamente ao criar
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate //Spring preenche a data automaticamente ao atualizar
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    //por padrao relacionamentos ToMany são Lazy fetch
    //@Transient
    @OneToMany(mappedBy = "autor",
            // cascade = CascadeType.ALL ,
            fetch = FetchType.LAZY)
    private List<Livro> livros = new ArrayList<>();
}