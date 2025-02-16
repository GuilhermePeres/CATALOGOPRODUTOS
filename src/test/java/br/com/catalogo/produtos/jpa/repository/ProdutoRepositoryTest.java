package br.com.catalogo.produtos.jpa.repository;

import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarProdutosEmLote() {
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();

        when(produtoRepository.saveAll(produtos)).thenReturn(produtos);

        //Act
        List<ProdutoEntity> produtosRegistrados = produtoRepository.saveAll(produtos);

        //Assert
        assertThat(produtosRegistrados)
                .isNotNull()
                .isEqualTo(produtos);

        verify(produtoRepository, times(1)).saveAll(argThat(argument ->
                argument instanceof List<ProdutoEntity>));
    }
}