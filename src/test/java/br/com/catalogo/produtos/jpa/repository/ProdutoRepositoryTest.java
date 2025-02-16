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
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirSalvarListaDeProdutos() {
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
    void devePermitirEncontrarProdutoPorId() {
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produtos.getFirst()));

        //Act
        Optional<ProdutoEntity> produtoEncontrado = produtoRepository.findById(produtos.getFirst().getId());

        //Assert
        assertThat(produtoEncontrado).isPresent();
        assertThat(produtoEncontrado.get().getId()).isEqualTo(produtos.getFirst().getId());
        assertThat(produtoEncontrado.get().getNome()).isEqualTo(produtos.getFirst().getNome());
        assertThat(produtoEncontrado.get().getDescricao()).isEqualTo(produtos.getFirst().getDescricao());
        assertThat(produtoEncontrado.get().getPreco()).isEqualTo(produtos.getFirst().getPreco());
        assertThat(produtoEncontrado.get().getQuantidadeEmEstoque()).isEqualTo(produtos.getFirst().getQuantidadeEmEstoque());
    }

    @Test
    void devePermitirEncontrarTodosOsProdutos() {
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();

        when(produtoRepository.findAll()).thenReturn(produtos);

        //Act
        List<ProdutoEntity> produtosRetornados = produtoRepository.findAll();

        // Assert
        assertThat(produtosRetornados)
                .hasSize(2)
                .isEqualTo(produtos);
    }
}