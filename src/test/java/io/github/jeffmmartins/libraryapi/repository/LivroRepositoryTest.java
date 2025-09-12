package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.model.GeneroLivro;
import io.github.jeffmmartins.libraryapi.model.Livro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository repository;

    @Autowired
    AutorRepository autorRepository;


    @Test
    void salvarTest(){
        Livro livro = new Livro();
        livro.setIsbn("9000-000");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        //esse autor que foi passado ele já precisa ter o ID.
        Autor autor = autorRepository
                .findById(UUID.fromString("2772557e-c048-4cb2-bc30-2278cb9a05c2"))
                .orElse(null);

        livro.setAutor(new Autor());

        repository.save(livro);
    }

    @Test
    void salvarCascadeTest(){
        Livro livro = new Livro();
        livro.setIsbn("9000-000");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Outro Livro");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950,1, 31));

        livro.setAutor(autor);

        repository.save(livro);
    }

    //Padrão, nesse tenho que lembrar de tirar o cascade
    @Test
    void salvarLivroeAutorTest(){
        Livro livro = new Livro();
        livro.setIsbn("9008989-900");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("Terceiro  Livro");
        livro.setDataPublicacao(LocalDate.of(1980,1,2));

        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileiro");
        autor.setDataNascimento(LocalDate.of(1950,1, 31));

        autorRepository.save(autor);

        livro.setAutor(autor);

        repository.save(livro);
    }

    //Teste para realizar a busca dos livros
    @Test
    void buscarLivros(){
        UUID id = UUID.fromString("c1bf9430-2ec2-465f-a7a3-6f3063588963");
        Livro livro = repository.findById(id).orElse(null);
        System.out.println("Livro: ");
        System.out.println(livro.getTitulo());
        System.out.println("Autor: ");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void pesquisaTituloTest(){
        List<Livro> lista = repository.findByTitulo("O roubo da casa assombrada");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorIsbn(){
        List<Livro> lista = repository.findByIsbn("99999-000");
        lista.forEach(System.out::println);
    }

    @Test
    void pesquisaPorTituloAndIsbnTest(){
        var preco = BigDecimal.valueOf(204.00);
        List<Livro> lista = repository.findByTituloAndPreco("O roubo da casa assombrada", preco);
        lista.forEach(System.out::println);
    }

    @Test
    void ListarLivrosComQueryJPQL(){
        var resultado = repository.listarTodosOrdenadosPorTituloAndPreco();
        resultado.forEach(System.out::println);
    }

    @Test
    void ListarAutoresDosLivros(){
        var resultado = repository.listarAutoresDosLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarTitulosNaoRepetidosDeLivros(){
        var resultado = repository.listarNomesDiferentesLivros();
        resultado.forEach(System.out::println);
    }

    @Test
    void listarGenerosDeLivrosAutoresBrasileiros(){
        var resultado = repository.listarGenerosAutoresBrasileiros();
        resultado.forEach(System.out::println);
    }
}