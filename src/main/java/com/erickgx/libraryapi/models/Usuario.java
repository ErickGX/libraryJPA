package com.erickgx.libraryapi.models;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.lang.reflect.Array;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String login;

    @Column
    private String senha;

    @Type(ListArrayType.class) //hypersistence-utils
    @Column(name = "roles", columnDefinition = "varchar[]")
    private List<String> roles;



}
