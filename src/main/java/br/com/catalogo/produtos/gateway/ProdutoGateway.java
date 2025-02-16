package br.com.catalogo.produtos.gateway;

import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;

import java.util.List;

public interface ProdutoGateway {
    RegistrarRespostaJson registrarProdutosEmLote(List<ProdutoBatch> produto);
    EstoqueRespostaJson atualizarProdutosPorPedido(Long idPedido, List<ItemPedidoReserva> itens);
    List<ProdutoJson> consultarProdutos();
}
