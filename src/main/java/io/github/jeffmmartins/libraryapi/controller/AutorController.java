package io.github.jeffmmartins.libraryapi.controller;

import io.github.jeffmmartins.libraryapi.controller.dto.AutorDTO;
import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

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

    //PathVariabel o valor desse parametro deve vir via URL exemplo /api/contas/1/deposito - path 1
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable String id){
        var idAutor = UUID.fromString(id);
        Optional<Autor> autorOptinal = autorService.obterPorId(idAutor);
        if(autorOptinal.isPresent()){
            //retorna a entidade, extrai o objeto Autor real de dentro do Optional
            Autor autor = autorOptinal.get();
            AutorDTO dto = new AutorDTO(autor.getId(),autor.getNome(),autor.getDataNascimento(),autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        //retorna informando que não foi encontrado.
        return ResponseEntity.notFound().build();
    }

    //COmo não vai retornar nada, o retorno é void, sempre que não vai na no body é utilizado o void
    //A chave de id é que indica o PathVariable é obrigatório
    //Deletar a resposta é como noContent
    @DeleteMapping("{id}")
    public ResponseEntity<Void> detletar(@PathVariable  String id){
        var autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(autorId);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        autorService.deletar(autorOptional.get());
        return ResponseEntity.noContent().build();
    }
}
