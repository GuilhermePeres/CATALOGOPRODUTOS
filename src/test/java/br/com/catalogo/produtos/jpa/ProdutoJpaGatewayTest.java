package br.com.catalogo.produtos.jpa;

import br.com.catalogo.produtos.domain.Produto;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.gateway.database.jpa.ProdutoJpaGateway;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class ProdutoJpaGatewayTest {
    @InjectMocks
    private ProdutoJpaGateway produtoJpaGateway;

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
        List<Produto> produtos = ProdutoHelper.gerarListaProduto();

        //Act
        RegistrarRespostaJson respostaJson = produtoJpaGateway.registrarProdutosEmLote(produtos);

        //Assert
        assertThat(respostaJson).isNotNull();
        assertThat(respostaJson.isProdutosRegistrados()).isTrue();

        verify(produtoRepository, times(1)).saveAll(anyList());
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
