package br.com.catalogo.produtos.domain;

import java.util.UUID;

public record ItemPedidoReserva(UUID produtoId, Integer quantidade) {

}
