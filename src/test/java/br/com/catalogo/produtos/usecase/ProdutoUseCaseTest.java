package br.com.catalogo.produtos.usecase;

import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.ProdutoGateway;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.usecase.rule.RuleBase;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ProdutoUseCaseTest {
    @InjectMocks
    private ProdutoUseCase produtoUseCase;

    @Mock
    private ProdutoGateway produtoGateway;

    @Mock
    private List<RuleBase> rules;

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
    void deveRegistrarProdutosEmLoteComSucesso() {
        //Arrange
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();
        RegistrarRespostaJson respostaEsperada = new RegistrarRespostaJson(true);

        when(produtoGateway.registrarProdutosEmLote(produtos)).thenReturn(respostaEsperada);

        //Act
        RegistrarRespostaJson resposta = produtoUseCase.registrarProdutosEmLote(produtos);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isProdutosRegistrados()).isTrue();

        verify(rules, times(produtos.size())).forEach(any());
        verify(produtoGateway, times(1)).registrarProdutosEmLote(produtos);
    }

    @Test
    @Disabled
    void deveAtualizarProdutosPorPedidoComSucesso() {
        //Arrange
        Long idPedido = 1L;
        List<ItemPedidoReserva> itens = ProdutoHelper.gerarListaItemPedidoReservaAleatorio();
        EstoqueRespostaJson respostaEsperada = new EstoqueRespostaJson(idPedido, true);

        when(produtoGateway.atualizarProdutosPorPedido(idPedido, itens)).thenReturn(respostaEsperada);

        //Act
        EstoqueRespostaJson resposta = produtoUseCase.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.getPedidoId()).isEqualTo(idPedido);
        assertThat(resposta.isEstoqueDisponivel()).isTrue();

        verify(produtoGateway, times(1)).atualizarProdutosPorPedido(idPedido, itens);
    }

    @Test
    void deveConsultarProdutosComSucesso() {
        //Arrange
        List<ProdutoJson> produtosEsperados = ProdutoHelper.gerarListaProdutoJson();

        when(produtoGateway.consultarProdutos()).thenReturn(produtosEsperados);

        //Act
        List<ProdutoJson> produtos = produtoUseCase.consultarProdutos();

        //Assert
        assertThat(produtos).hasSize(produtosEsperados.size());

        for(int i=0; i<produtos.size(); i++){
            ProdutoJson produtoRetornado = produtos.get(i);
            ProdutoJson produtoEsperado = produtosEsperados.get(i);

            assertThat(produtoRetornado.getId()).isEqualTo(produtoEsperado.getId());
            assertThat(produtoRetornado.getNome()).isEqualTo(produtoEsperado.getNome());
            assertThat(produtoRetornado.getDescricao()).isEqualTo(produtoEsperado.getDescricao());
            assertThat(produtoRetornado.getPreco()).isEqualTo(produtoEsperado.getPreco());
            assertThat(produtoRetornado.getQuantidadeEmEstoque()).isEqualTo(produtoEsperado.getQuantidadeEmEstoque());
        }

        verify(produtoGateway, times(1)).consultarProdutos();
    }

    @Test
    void deveValidarRegrasComSucesso() {
        //Arrange
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();

        //Act
        produtoUseCase.registrarProdutosEmLote(produtos);

        //Assert
        verify(rules, times(produtos.size())).forEach(any());
    }
}
