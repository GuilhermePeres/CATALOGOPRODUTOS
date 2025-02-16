package br.com.catalogo.produtos.controller.api.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoReservaJson {
    private Long produtoId;
    private Integer quantidade;
}
