package br.com.catalogo.produtos.usecase;

import br.com.catalogo.produtos.controller.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.Produto;
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

    private final List<RuleBase> rules;

    @Autowired
    public ProdutoUseCase(ProdutoGateway produtoGateway, List<RuleBase> rules) {
        this.produtoGateway = produtoGateway;
        this.rules = rules;
    }

    public RegistrarRespostaJson registrarProdutosEmLote(List<Produto> produtoList){
        produtoList.forEach(this::validaRegras);

        return produtoGateway.registrarProdutosEmLote(produtoList);
    }

    public EstoqueRespostaJson atualizarProdutosPorPedido(Long idPedido, List<ItemPedidoReserva> itens){
        return produtoGateway.atualizarProdutosPorPedido(idPedido, itens);
    }

    public List<ProdutoJson> consultarProdutos() {
        return produtoGateway.consultarProdutos();
    }

    private void validaRegras(Produto novoProduto){
        var inputDto = new InputDto(novoProduto);

        rules.forEach(r -> r.validate(inputDto));
    }
}
