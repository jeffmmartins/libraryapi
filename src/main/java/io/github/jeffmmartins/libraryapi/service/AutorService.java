package io.github.jeffmmartins.libraryapi.service;

import io.github.jeffmmartins.libraryapi.model.Autor;
import io.github.jeffmmartins.libraryapi.repository.AutorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor salvar(Autor autor){
        return autorRepository.save(autor);
    }

    //Usa-se o Optional porque pode haver o autor ou nao.
    public Optional<Autor> obterPorId(UUID id){
       return autorRepository.findById(id);
    }
}
