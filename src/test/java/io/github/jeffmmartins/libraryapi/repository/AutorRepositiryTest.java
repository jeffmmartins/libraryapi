package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.model.GeneroLivro;
import io.github.jeffmmartins.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositiryTest {

    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950,1, 31));

        Autor autor1 = new Autor();
        autor1.setNome("Jeff");
        autor1.setNacionalidade("inglês");
        autor1.setDataNascimento(LocalDate.of(1950,1, 31));

        var autorSalvo = repository.save(autor1);
        System.out.println("Autor Salvo: " + autorSalvo);
    }

    @Test
    public void atualizarTest(){
        //transformando uma string em UUID
        var id = UUID.fromString("a76c3a09-29c8-48ee-8443-51521a45dcca");

        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get() ;
            System.out.println("Dados do Autor: ");
            //retorna o objeto que está la dentro
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1960,1,30));
            //esse metodo tanto salva como atualiza
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listarTest(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void countTest(){
        System.out.println("Contagem de autores" + repository.count());
    }

    @Test
    public void deletePorIdTest(){
        var id = UUID.fromString("a76c3a09-29c8-48ee-8443-51521a45dcca");
        repository.deleteById(id);
    }

    //Deletando o objeto
    @Test
    public void deleteTest(){
        var id = UUID.fromString("e74022ca-6a27-48e6-aaf5-7ac90d0a3a64");
        var maria = repository.findById(id).get();
        repository.delete(maria);
    }

    @Test
    public void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Americano");
        autor.setDataNascimento(LocalDate.of(1970,8, 5));

        Livro livro = new Livro();
        livro.setIsbn("9000-000");
        livro.setPreco(BigDecimal.valueOf(204));
        livro.setGenero(GeneroLivro.MISTERIO);
        livro.setTitulo("O roubo da casa assombrada");
        livro.setDataPublicacao(LocalDate.of(1999,1,2));
        //setando o autor indicando que o livro é do autor
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("99999-000");
        livro2.setPreco(BigDecimal.valueOf(650));
        livro2.setGenero(GeneroLivro.MISTERIO);
        livro2.setTitulo("O roubo da casa assombrada");
        livro2.setDataPublicacao(LocalDate.of(2000,1,2));
        livro2.setAutor(autor);

        //Adicionando os livros na lista de Autor
        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);

        livroRepository.saveAll(autor.getLivros());
    }

    @Test
    //@Transactional umas das formas de realizar.
    void listarLivrosAutor(){
        var id = UUID.fromString("9dd789b8-ca4f-48bb-be53-ea14f044c3b5");
        var autor = repository.findById(id).get();

        //buscar os livros do autor
        List<Livro> livrosLista = livroRepository.findByAutor(autor);
        autor.setLivros(livrosLista);

        //metodo de referencia que vai ser executado para cada um deles.
        autor.getLivros().forEach(System.out::println);


    }
}
