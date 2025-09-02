package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
