package br.com.catalogo.produtos.gateway;

import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;

public interface PedidoGateway {
    void enviarRespostaEstoque(EstoqueRespostaJson estoqueRespostaJson);
}
