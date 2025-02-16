package br.com.catalogo.produtos.controller.queue.json;

import br.com.catalogo.produtos.controller.api.json.ItemPedidoReservaJson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaProdutoJson {
    private Long pedidoId;
    private Long clienteId;
    private List<ItemPedidoReservaJson> itens;
}
