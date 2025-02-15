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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProdutoRepositoryTest {

    @Mock
    private ProdutoRepository produtoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarProdutosEmLote(){
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

    @Test
    void devePermitirBuscarProduto(){
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();

        when(produtoRepository.findByNome(produtos.getFirst().getNome())).thenReturn(Optional.of(produtos.getFirst()));

        //Act
        Optional<ProdutoEntity> produtoEntityRecebido = produtoRepository.findByNome(produtos.getFirst().getNome());

        //Assert
        assertThat(produtoEntityRecebido)
                .isPresent()
                .containsSame(produtos.getFirst());

        produtoEntityRecebido.ifPresent(produtoEntity -> {
            assertThat(produtoEntity.getId()).isEqualTo(produtos.getFirst().getId());
            assertThat(produtoEntity.getNome()).isEqualTo(produtos.getFirst().getNome());
            assertThat(produtoEntity.getDescricao()).isEqualTo(produtos.getFirst().getDescricao());
            assertThat(produtoEntity.getPreco()).isEqualTo(produtos.getFirst().getPreco());
            assertThat(produtoEntity.getQuantidadeEmEstoque()).isEqualTo(produtos.getFirst().getQuantidadeEmEstoque());
        });

        verify(produtoRepository, times(1)).findByNome(any(String.class));
    }
}
