package br.com.catalogo.produtos.jpa.repository;

import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ProdutoRepositoryIT {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void devePermitirCriarTabela(){
        var totalRegistros = produtoRepository.count();

        assertThat(totalRegistros).isEqualTo(3);
    }
}