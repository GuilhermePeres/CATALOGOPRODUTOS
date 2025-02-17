package br.com.catalogo.produtos;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class CatalogoprodutosApplicationIT {

    @Autowired
    private CatalogoprodutosApplication catalogoprodutosApplication;

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads(){
        assertThat(context).isNotNull();
    }
}
