package br.com.catalogo.produtos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaProduto {
    private Long pedidoId;
    private Long clienteId;
    private List<ItemPedidoReserva> itens;
}
