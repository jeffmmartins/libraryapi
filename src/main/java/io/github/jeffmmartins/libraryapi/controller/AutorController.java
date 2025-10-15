package io.github.jeffmmartins.libraryapi.controller;

import io.github.jeffmmartins.libraryapi.controller.dto.AutorDTO;
import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

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
    // No ResponseEntity o tipo é void porque não tem infomração no body.
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        Autor autorEntidade = autor.mapearParaAutor();
        autorService.salvar(autorEntidade);
        //Apos o passo acima, o objeto tem um id
        // código abaixo é para criar isso: http://localhost:8080/autores/889y4863275285625edgyufd

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(autorEntidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
