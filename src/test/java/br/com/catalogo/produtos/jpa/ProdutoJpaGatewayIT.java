package br.com.catalogo.produtos.jpa;

import br.com.catalogo.produtos.controller.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.gateway.database.jpa.ProdutoJpaGateway;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ProdutoJpaGatewayIT {

    @Autowired
    private ProdutoJpaGateway produtoJpaGateway;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void devePermitirRegistrarProdutosEmLote() {
        //Arrange
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();

        //Act
        RegistrarRespostaJson respostaJson = produtoJpaGateway.registrarProdutosEmLote(produtos);

        //Assert
        assertThat(respostaJson).isNotNull();
        assertThat(respostaJson.isProdutosRegistrados()).isTrue();
    }

    @Test
    void deveAtualizarProdutosEmLoteComSucesso() {
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();
        produtos.getFirst().setId(1L);
        produtos.get(1).setId(2L);

        //Act
        produtoJpaGateway.atualizarProdutosEmLote(produtos);

        //Assert
        List<ProdutoEntity> produtosBuscados = produtoRepository.findAll();

        assertThat(produtosBuscados).hasSize(3);
        assertThat(produtosBuscados.getFirst().getId()).isEqualTo(produtos.getFirst().getId());
        assertThat(produtosBuscados.getFirst().getNome()).isEqualTo(produtos.getFirst().getNome());
        assertThat(produtosBuscados.getFirst().getDescricao()).isEqualTo(produtos.getFirst().getDescricao());
        assertThat(produtosBuscados.getFirst().getPreco()).isEqualTo(produtos.getFirst().getPreco());
        assertThat(produtosBuscados.getFirst().getQuantidadeEmEstoque()).isEqualTo(produtos.getFirst().getQuantidadeEmEstoque());

        assertThat(produtosBuscados.get(1).getId()).isEqualTo(produtos.get(1).getId());
        assertThat(produtosBuscados.get(1).getNome()).isEqualTo(produtos.get(1).getNome());
        assertThat(produtosBuscados.get(1).getDescricao()).isEqualTo(produtos.get(1).getDescricao());
        assertThat(produtosBuscados.get(1).getPreco()).isEqualTo(produtos.get(1).getPreco());
        assertThat(produtosBuscados.get(1).getQuantidadeEmEstoque()).isEqualTo(produtos.get(1).getQuantidadeEmEstoque());
    }

    @Test
    void deveAtualizarProdutosPorPedidoComSucesso() {
        //Arrange
        Long idPedido = 10L;
        List<ItemPedidoReserva> itens = List.of(
                new ItemPedidoReserva(1L,10),
                new ItemPedidoReserva(2L,10)
        );

        //Act
        EstoqueRespostaJson resposta = produtoJpaGateway.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isEstoqueDisponivel()).isTrue();
        assertThat(resposta.getPedidoId()).isEqualTo(idPedido);

        Optional<ProdutoEntity> produtoAtualizado = produtoRepository.findById(itens.getFirst().produtoId());
        assertThat(produtoAtualizado).isPresent();
        assertThat(produtoAtualizado.get().getQuantidadeEmEstoque()).isZero();

        Optional<ProdutoEntity> produtoAtualizado2 = produtoRepository.findById(itens.get(1).produtoId());
        assertThat(produtoAtualizado2).isPresent();
        assertThat(produtoAtualizado2.get().getQuantidadeEmEstoque()).isZero();
    }

    @Test
    void deveAtualizarProdutosPorPedidoProdutoNaoEncontrado() {
        //Arrange
        Long idPedido = 10L;
        List<ItemPedidoReserva> itens = ProdutoHelper.gerarListaItemPedidoReservaAleatorio();

        //Act
        EstoqueRespostaJson resposta = produtoJpaGateway.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isEstoqueDisponivel()).isFalse();
        assertThat(resposta.getPedidoId()).isEqualTo(idPedido);
    }

    @Test
    void deveAtualizarProdutosPorPedidoEstoqueInsuficiente() {
        //Arrange
        Long idPedido = 10L;
        List<ItemPedidoReserva> itens = List.of(
                new ItemPedidoReserva(1L,20),
                new ItemPedidoReserva(2L,20)
        );

        //Act
        EstoqueRespostaJson resposta = produtoJpaGateway.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isEstoqueDisponivel()).isFalse();
        assertThat(resposta.getPedidoId()).isEqualTo(idPedido);
    }

    @Test
    void deveConsultarProdutosComSucesso() {
        //Act
        List<ProdutoJson> resultado = produtoJpaGateway.consultarProdutos();

        //Assert
        assertThat(resultado).hasSize(3);
        assertThat(resultado.getFirst().getId()).isEqualTo(1L);
        assertThat(resultado.get(1).getId()).isEqualTo(2L);
        assertThat(resultado.get(2).getId()).isEqualTo(3L);
    }
}
