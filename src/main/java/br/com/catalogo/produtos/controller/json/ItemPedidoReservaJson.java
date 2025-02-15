package br.com.catalogo.produtos.controller.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoReservaJson {
    private UUID produtoId;
    private Integer quantidade;
}
