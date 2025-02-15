package br.com.catalogo.produtos.gateway.api.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueRespostaJson {
    private Long pedidoId;
    private boolean estoqueDisponivel;
}
