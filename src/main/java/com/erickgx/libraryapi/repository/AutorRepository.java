package com.erickgx.libraryapi.repository;

import com.erickgx.libraryapi.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
