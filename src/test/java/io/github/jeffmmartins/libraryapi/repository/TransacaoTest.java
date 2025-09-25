package io.github.jeffmmartins.libraryapi.repository;

import io.github.jeffmmartins.libraryapi.service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransacaoTest {

    @Autowired
    TransacaoService transacaoService;

    /**
     * Commit -> confirmar as alterações
     * Roll Back -> desfazer as alterações
     */
    //Sempre que for necessário realizar uma alteração de escrita no banco de dados é feito uma transação
    @Test
     //abre a transação e no final é realizado um commit ou roll back
    void TransacaoSImples(){
        transacaoService.executar();
    }

}
