package io.github.jeffmmartins.libraryapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "isbn", length = 20, nullable = false)
    private String isbn;

    @Column(name = "titulo", length = 150, nullable = false)
    private String titulo;

    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    @Column(name = "genero", length = 30, nullable = false)
    private String genero;

    @Column(name = "preco", precision = 18, scale = 2)
    private Double preco;

    @JoinColumn(name = "id_autor")
    @ManyToOne
    private Autor autor;

}
