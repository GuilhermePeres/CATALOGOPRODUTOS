package br.com.catalogo.produtos.controller.queue;

import br.com.catalogo.produtos.controller.api.json.ItemPedidoReservaJson;
import br.com.catalogo.produtos.controller.queue.json.ReservaProdutoJson;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.EnableTestBinder;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@EnableTestBinder
class ProdutoEntrypointIT {

    @Autowired
    private ProdutoEntrypoint produtoEntryPoint;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProdutoRepository produtoRepository;

    private Consumer<String> consumidor;

    @BeforeEach
    void setUp() {
        consumidor = produtoEntryPoint.novoPedido();
    }

    @Test
    void deveDecrementarEstoqueQuandoReceberPedido() throws Exception {
        // Arrange
        ReservaProdutoJson reservaProdutoJson = new ReservaProdutoJson(1L, 2L, List.of(new ItemPedidoReservaJson(1L, 1)));
        String mensagem = objectMapper.writeValueAsString(reservaProdutoJson);

        // Act
        consumidor.accept(mensagem);

        // Assert
        Optional<ProdutoEntity> produto = produtoRepository.findById(1L);
        assertThat(produto).isPresent();
        ProdutoEntity produtoEntity = produto.get();
        assertThat(produtoEntity.getQuantidadeEmEstoque()).isEqualTo(9);
    }
}
