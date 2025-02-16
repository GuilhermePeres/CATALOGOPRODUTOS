package br.com.catalogo.produtos.jpa;

import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.exception.ErroAoAcessarRepositorioException;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

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
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();

        //Act
        RegistrarRespostaJson respostaJson = produtoJpaGateway.registrarProdutosEmLote(produtos);

        //Assert
        assertThat(respostaJson).isNotNull();
        assertThat(respostaJson.isProdutosRegistrados()).isTrue();

        verify(produtoRepository, times(1)).saveAll(anyList());
    }

    @Test
    void deveLancarErroAoAcessarRepositorioExceptionAoRegistrarProdutosEmLote(){
        //Arrange
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();
        when(produtoRepository.saveAll(anyList())).thenThrow(new RuntimeException());

        //Act Assert
        assertThatThrownBy(() -> produtoJpaGateway.registrarProdutosEmLote(produtos))
                .isInstanceOf(ErroAoAcessarRepositorioException.class);
    }


    @Test
    void deveAtualizarProdutosEmLoteComSucesso() {
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();

        //Act
        produtoJpaGateway.atualizarProdutosEmLote(produtos);

        //Assert
        verify(produtoRepository, times(1)).saveAll(produtos);
    }

    @Test
    void deveLancarErroAoAcessarRepositorioExceptionAoAtualizarProdutosEmLote() {
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();
        when(produtoRepository.saveAll(produtos)).thenThrow(new RuntimeException());

        //Act Assert
        assertThatThrownBy(() -> produtoJpaGateway.atualizarProdutosEmLote(produtos))
                .isInstanceOf(ErroAoAcessarRepositorioException.class);
    }

    @Test
    void deveAtualizarProdutosPorPedidoComSucesso() {
        //Arrange
        Long idPedido = 1L;
        List<ItemPedidoReserva> itens = ProdutoHelper.gerarListaItemPedidoReservaAleatorio();
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produtos.getFirst()));

        //Act
        EstoqueRespostaJson resposta = produtoJpaGateway.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isEstoqueDisponivel()).isTrue();
        assertThat(resposta.getPedidoId()).isEqualTo(idPedido);

        verify(produtoRepository, times(itens.size())).findById(anyLong());
        verify(produtoRepository, times(1)).saveAll(anyList());
    }

    @Test
    void deveAtualizarProdutosPorPedidoProdutoNaoEncontrado() {
        //Arrange
        Long idPedido = 1L;
        List<ItemPedidoReserva> itens = ProdutoHelper.gerarListaItemPedidoReservaAleatorio();

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.empty());

        //Act
        EstoqueRespostaJson resposta = produtoJpaGateway.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isEstoqueDisponivel()).isFalse();
        assertThat(resposta.getPedidoId()).isEqualTo(idPedido);

        verify(produtoRepository, times(1)).findById(anyLong());
    }

    @Test
    void deveAtualizarProdutosPorPedidoEstoqueInsuficiente() {
        //Arrange
        Long idPedido = 1L;
        List<ItemPedidoReserva> itens = ProdutoHelper.gerarListaItemPedidoReservaAleatorio();
        List<ProdutoEntity> produto = ProdutoHelper.gerarListaProdutoEntity();
        produto.getFirst().setQuantidadeEmEstoque(0);

        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(produto.getFirst()));

        //Act
        EstoqueRespostaJson resposta = produtoJpaGateway.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isEstoqueDisponivel()).isFalse();
        assertThat(resposta.getPedidoId()).isEqualTo(idPedido);

        verify(produtoRepository, times(1)).findById(anyLong());
    }

    @Test
    void deveLancarErroAoAcessarRepositorioExceptionAoAtualizarProdutosPorPedido() {
        //Arrange
        Long idPedido = 1L;
        List<ItemPedidoReserva> itens = ProdutoHelper.gerarListaItemPedidoReservaAleatorio();

        when(produtoRepository.findById(anyLong())).thenThrow(new RuntimeException());

        //Act Assert
        assertThatThrownBy(() -> produtoJpaGateway.atualizarProdutosPorPedido(idPedido, itens))
                .isInstanceOf(ErroAoAcessarRepositorioException.class);
    }

    @Test
    void deveConsultarProdutosComSucesso() {
        //Arrange
        List<ProdutoEntity> produtosEntity = ProdutoHelper.gerarListaProdutoEntity();

        when(produtoRepository.findAll()).thenReturn(produtosEntity);

        //Act
        List<ProdutoJson> resultado = produtoJpaGateway.consultarProdutos();

        //Assert
        assertThat(resultado).hasSize(produtosEntity.size());

        for(int i=0; i<resultado.size(); i++){
            ProdutoJson produtoJson = resultado.get(i);
            ProdutoEntity produtoEntity = produtosEntity.get(i);

            assertThat(produtoJson.getId()).isEqualTo(produtoEntity.getId());
            assertThat(produtoJson.getNome()).isEqualTo(produtoEntity.getNome());
            assertThat(produtoJson.getDescricao()).isEqualTo(produtoEntity.getDescricao());
            assertThat(produtoJson.getPreco()).isEqualTo(produtoEntity.getPreco());
            assertThat(produtoJson.getQuantidadeEmEstoque()).isEqualTo(produtoEntity.getQuantidadeEmEstoque());
        }

        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    void deveLancarErroAoAcessarRepositorioExceptionAoConsultarProdutos() {
        //Arrange
        when(produtoRepository.findAll()).thenThrow(new RuntimeException());

        //Act Assert
        assertThatThrownBy(() -> produtoJpaGateway.consultarProdutos())
                .isInstanceOf(ErroAoAcessarRepositorioException.class);
    }
}
