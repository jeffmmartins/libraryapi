package io.github.jeffmmartins.libraryapi.controller.dto;

import io.github.jeffmmartins.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

//Classe imutavel, não é possivel mudar os valores
public record AutorDTO(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {

    //Esse metodo cria o autor e preenche os dados que vem no DTO
    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}
