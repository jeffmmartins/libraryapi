package io.github.jeffmmartins.libraryapi.controller;

import io.github.jeffmmartins.libraryapi.controller.dto.AutorDTO;
import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores") // mapear a url que o controller vai ficar escutando.
// http://localhost:8080/autores
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @PostMapping
    // quando receber json para /autores vai mapear para o objeto AutorDTO
    public ResponseEntity salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.mapearParaAutor();
        autorService.salvar(autorEntidade);
        return new ResponseEntity("Autor salvo com sucesso" + autor, HttpStatus.CREATED);
    }
}
