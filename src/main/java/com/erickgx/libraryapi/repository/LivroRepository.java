package com.erickgx.libraryapi.repository;

import com.erickgx.libraryapi.models.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
