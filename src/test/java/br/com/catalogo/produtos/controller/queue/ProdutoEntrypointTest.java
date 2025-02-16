package br.com.catalogo.produtos.controller.queue;

import br.com.catalogo.produtos.controller.api.json.ItemPedidoReservaJson;
import br.com.catalogo.produtos.controller.queue.json.ReservaProdutoJson;
import br.com.catalogo.produtos.usecase.ProdutoUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoEntrypointTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProdutoUseCase produtoUseCase;

    @InjectMocks
    private ProdutoEntrypoint produtoEntryPoint;

    private Consumer<String> consumidor;

    @BeforeEach
    void setUp() {
        consumidor = produtoEntryPoint.novoPedido();
    }

    @Test
    void deveProcessarMensagemComSucesso() throws Exception {
        // Arrange
        ReservaProdutoJson reservaProdutoJson = new ReservaProdutoJson(1L, 2L,
                List.of(new ItemPedidoReservaJson(1L, 1)));
        String mensagem = "{\"pedidoId\":1,\"clienteId\":2,\"itens\":[]}";
        when(objectMapper.readValue(mensagem, ReservaProdutoJson.class)).thenReturn(reservaProdutoJson);

        // Act
        consumidor.accept(mensagem);

        // Assert
        verify(produtoUseCase, times(1)).atualizarProdutosPorPedido(eq(1L), anyList());
    }

    @Test
    void naoDeveProcessarMensagemComFormatoInvalido() throws Exception {
        // Arrange
        String mensagemInvalida = "{ invalid json }";
        when(objectMapper.readValue(mensagemInvalida, ReservaProdutoJson.class)).thenThrow(JsonProcessingException.class);

        // Act
        consumidor.accept(mensagemInvalida);

        // Assert
        verifyNoInteractions(produtoUseCase);
    }
}
