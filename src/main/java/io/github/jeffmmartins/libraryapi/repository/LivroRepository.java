package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //query method é por convenção quando quer trazer algo utilizar o findBy + nome da propridade
    //consulta : select * from livro where id_autor = id, basta declarar o metodo. foi passado o autro retornando uma lista de livros.
    List<Livro> findByAutor (Autor autor);

    //Realizando uma pesquisa pelo titulo
    //consulta: select * from livro where titulo = titulo
    List<Livro> findByTitulo (String titulo);

    //no caso se for um valor unico, posso fazer assim não usaria List
    List<Livro> findByIsbn (String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);
}
