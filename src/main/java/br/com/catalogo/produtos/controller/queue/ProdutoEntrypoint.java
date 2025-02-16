package br.com.catalogo.produtos.controller.queue;

import br.com.catalogo.produtos.controller.api.json.ItemPedidoReservaJson;
import br.com.catalogo.produtos.controller.queue.json.ReservaProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ReservaProduto;
import br.com.catalogo.produtos.usecase.ProdutoUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Component
@AllArgsConstructor
@Slf4j
public class ProdutoEntrypoint {

    private final ObjectMapper objectMapper;
    private final ProdutoUseCase produtoUseCase;

    @Bean
    public Consumer<String> novoPedido() {
        return message -> {
            log.info("Novo pedido recebido");
            try {
                ReservaProduto reservaProduto = mapToDomain(objectMapper.readValue(message, ReservaProdutoJson.class));

                produtoUseCase.atualizarProdutosPorPedido(reservaProduto.getPedidoId(), reservaProduto.getItens());
            } catch (JsonProcessingException e) {
                log.error("Erro ao ler evento enviado pelo sistema de pedidos: ", e);
            }
        };
    }

    private ReservaProduto mapToDomain(ReservaProdutoJson reservaProdutoJson){
        List<ItemPedidoReserva> itensPedidoReserva = new ArrayList<>();

        for (ItemPedidoReservaJson itemJson : reservaProdutoJson.getItens()) {
            ItemPedidoReserva itemPedidoReserva = new ItemPedidoReserva(
                    itemJson.getProdutoId(),
                    itemJson.getQuantidade()
            );
            itensPedidoReserva.add(itemPedidoReserva);
        }

        return new ReservaProduto(
                reservaProdutoJson.getPedidoId(),
                reservaProdutoJson.getClienteId(),
                itensPedidoReserva
        );
    }
}
