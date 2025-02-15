package br.com.catalogo.produtos.gateway;

import br.com.catalogo.produtos.controller.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.Produto;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;

import java.util.List;

public interface ProdutoGateway {
    RegistrarRespostaJson registrarProdutosEmLote(List<Produto> produto);
    EstoqueRespostaJson atualizarProdutosPorPedido(Long idPedido, List<ItemPedidoReserva> itens);
    List<ProdutoJson> consultarProdutos();
}
