package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.model.GeneroLivro;
import io.github.jeffmmartins.libraryapi.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @see LivroRepositoryTest
 */

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

    //JPQL -> Referencias as entidades e as propriedades
    @Query(" select l from Livro as l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadosPorTituloAndPreco();

    //Selecionando os autores dos livros
    @Query("select a from Livro l join l.autor a")
    List<Autor> listarAutoresDosLivros();

    //select distinct l.* from l
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
            select l.genero
            from Livro l 
            join l.autor a 
            where a.nacionalidade = 'Brasileira'
            order by l.genero 
            """)
    List<String> listarGenerosAutoresBrasileiros();

    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao")
    List<Livro> findByGenero(@Param("genero") GeneroLivro generLivro, @Param("paramOrdenacao") String nomePropriedade);
}
