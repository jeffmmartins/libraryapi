package io.github.jeffmmartins.libraryapi.service;

import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.model.GeneroLivro;
import io.github.jeffmmartins.libraryapi.model.Livro;
import io.github.jeffmmartins.libraryapi.repository.AutorRepository;
import io.github.jeffmmartins.libraryapi.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Transactional // so funciona em metodo public
    public void executar(){
        //salva o autor
        Autor autor = new Autor();
        autor.setNome("Francisca ");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950,1, 31));
        autorRepository.save(autor);


        //Salva o livro
        Livro livro = new Livro();
        livro.setIsbn("9008989-900");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Livro da Francisca");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));


        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals("José")){
            throw new RuntimeException("Rollback");
        }

    }

}
