package br.com.catalogo.produtos.usecase;


import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.ProdutoGateway;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import br.com.catalogo.produtos.usecase.rule.RuleBase;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@EnableTestBinder
@ActiveProfiles("test")
@Transactional
class ProdutoUseCaseIT {

    @Autowired
    private ProdutoUseCase produtoUseCase;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoGateway produtoGateway;

    @Autowired
    private List<RuleBase> rules;

    @Test
    void deveRegistrarProdutosEmLoteComSucesso() {
        //Arrange
        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();

        //Act
        RegistrarRespostaJson resposta = produtoUseCase.registrarProdutosEmLote(produtos);

        //Assert
        assertThat(resposta).isNotNull();
        assertThat(resposta.isProdutosRegistrados()).isTrue();
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
        produtoUseCase.atualizarProdutosPorPedido(idPedido, itens);

        //Assert
        Optional<ProdutoEntity> produtoAtualizado = produtoRepository.findById(itens.getFirst().produtoId());
        assertThat(produtoAtualizado).isPresent();
        assertThat(produtoAtualizado.get().getQuantidadeEmEstoque()).isZero();

        Optional<ProdutoEntity> produtoAtualizado2 = produtoRepository.findById(itens.get(1).produtoId());
        assertThat(produtoAtualizado2).isPresent();
        assertThat(produtoAtualizado2.get().getQuantidadeEmEstoque()).isZero();
    }

    @Test
    void deveConsultarProdutosComSucesso() {
        //Act
        List<ProdutoJson> produtos = produtoUseCase.consultarProdutos();

        //Assert
        assertThat(produtos).hasSize(3);
        assertThat(produtos.getFirst().getId()).isEqualTo(1L);
        assertThat(produtos.get(1).getId()).isEqualTo(2L);
        assertThat(produtos.get(2).getId()).isEqualTo(3L);
    }
}
