package io.github.jeffmmartins.libraryapi.controller;

import io.github.jeffmmartins.libraryapi.controller.dto.AutorDTO;
import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.service.AutorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
    //@PathVariable é usada para extrair (receber) informações que vêm diretamente no caminho (path) da URL.
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

    //Ter cuidado quando estiver dois get para passar apenas a um parametro em um deles.
    //required = false indica que o query param não é obrigatorio, vai entrar na função mesmo quando não for passado nada
    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false)String nacionalidade){

            //Lista de autor, mas preciso transformar para lista de AutorDTO utiliza o stream.map que é
            //mapeando o autor para o autorSTo
            List<Autor> resultado = autorService.pesuqisa(nome, nacionalidade);
            //pega uma stream de autores, transforma em uma stream de autorDTO, converter ele para uma List e chama o collectors
            List<AutorDTO> lista = resultado.
                    stream().
                    map(autor -> new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade())).collect(Collectors.toList());
            return ResponseEntity.ok(lista);
    }

    //COmo não vai retornar nada no body, aplico como void.
    //mapeado para receber o id como parametro na url
    //@RequestBody o que vem no JSON vai ser transformando no objeto dto e @PathVariable("id")  caminho para localizar o id
    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id,@RequestBody AutorDTO dto){

        //Verificando se o autor existe.
        var autorId = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(autorId);
        if (autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);
    }

}
