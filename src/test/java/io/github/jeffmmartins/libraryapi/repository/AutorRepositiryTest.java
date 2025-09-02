package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Autor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class AutorRepositiryTest {

    @Autowired
    AutorRepository repository;

    @Test
    public void salvarTest(){
        Autor autor = new Autor();
        autor.setNome("Jose");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950,1, 31));

        var autorSalvo = repository.save(autor);
        System.out.println("Autor Salvo: " + autorSalvo);
    }
}
