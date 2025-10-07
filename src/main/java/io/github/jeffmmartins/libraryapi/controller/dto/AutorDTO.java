package io.github.jeffmmartins.libraryapi.controller.dto;

import java.time.LocalDate;

//Classe imutavel, não é possivel mudar os valores
public record AutorDTO(String nome, LocalDate dataNascimento, String nacionalidade) {

}
