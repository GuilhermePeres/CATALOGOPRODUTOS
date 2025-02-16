package br.com.catalogo.produtos.usecase;

import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.PedidoGateway;
import br.com.catalogo.produtos.gateway.ProdutoGateway;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.usecase.rule.RuleBase;
import br.com.catalogo.produtos.usecase.rule.dto.InputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoUseCase {

    private final ProdutoGateway produtoGateway;
    private final PedidoGateway pedidoGateway;

    private final List<RuleBase> rules;

    @Autowired
    public ProdutoUseCase(ProdutoGateway produtoGateway, PedidoGateway pedidoGateway, List<RuleBase> rules) {
        this.produtoGateway = produtoGateway;
        this.rules = rules;
        this.pedidoGateway = pedidoGateway;
    }

    public RegistrarRespostaJson registrarProdutosEmLote(List<ProdutoBatch> produtoList){
        produtoList.forEach(this::validaRegras);

        return produtoGateway.registrarProdutosEmLote(produtoList);
    }

    public EstoqueRespostaJson atualizarProdutosPorPedido(Long idPedido, List<ItemPedidoReserva> itens){
        EstoqueRespostaJson estoqueRespostaJson = produtoGateway.atualizarProdutosPorPedido(idPedido, itens);

        pedidoGateway.enviarRespostaEstoque(estoqueRespostaJson);

        return estoqueRespostaJson;
    }

    public List<ProdutoJson> consultarProdutos() {
        return produtoGateway.consultarProdutos();
    }

    private void validaRegras(ProdutoBatch novoProduto){
        var inputDto = new InputDto(novoProduto);

        rules.forEach(r -> r.validate(inputDto));
    }
}
