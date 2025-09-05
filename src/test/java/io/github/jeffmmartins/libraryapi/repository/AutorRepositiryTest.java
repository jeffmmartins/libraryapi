package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.input.LineSeparatorDetector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositiryTest {

    @Autowired
    AutorRepository repository;

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
}
