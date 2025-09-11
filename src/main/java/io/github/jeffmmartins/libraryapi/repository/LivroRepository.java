package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //query method é por convenção quando quer trazer algo utilizar o findBy + nome da propridade
    //consulta : select * from livro where id_autor = id, basta declarar o metodo.
    List<Livro> findByAutor (Autor autor);
}
