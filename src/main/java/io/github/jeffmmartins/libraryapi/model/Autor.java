package io.github.jeffmmartins.libraryapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "autor", schema = "public")
@Getter
@Setter
@ToString(exclude = "livros")
public class Autor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID) //gera o id automatico.
    private UUID id;

    @Column(name = "nome", length = 100, nullable = false) // varchar informa a quantidades de letras, aqui usando o length.
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "nacionalidade", length = 50, nullable = false)
    private String nacionalidade;

    //É eralizado o cadastro do autor e depois adiciona osm livros dentro de uma lista
    @OneToMany(mappedBy = "autor") // um autor pode ter muitos livros.
    private List<Livro> livros;
}
