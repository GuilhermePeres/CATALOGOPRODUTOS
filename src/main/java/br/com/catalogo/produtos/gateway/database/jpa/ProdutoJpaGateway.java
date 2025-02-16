package br.com.catalogo.produtos.gateway.database.jpa;

import br.com.catalogo.produtos.controller.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.exception.ErroAoAcessarRepositorioException;
import br.com.catalogo.produtos.gateway.ProdutoGateway;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProdutoJpaGateway implements ProdutoGateway {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoJpaGateway(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public RegistrarRespostaJson registrarProdutosEmLote(List<ProdutoBatch> produto){
        try{
            List<ProdutoEntity> produtoEntityList = mapToEntity(produto);

            produtoRepository.saveAll(produtoEntityList);

            return new RegistrarRespostaJson(true);

        }catch (Exception e){
            throw new ErroAoAcessarRepositorioException();
        }
    }

    private void atualizarProdutosEmLote(List<ProdutoEntity> produtos){
        try{
            produtoRepository.saveAll(produtos);

        }catch (Exception e){
            throw new ErroAoAcessarRepositorioException();
        }
    }

    @Override
    public EstoqueRespostaJson atualizarProdutosPorPedido(Long idPedido, List<ItemPedidoReserva> itens){
        List<ProdutoEntity> produtoList = new ArrayList<>(List.of());

        try{
            for(ItemPedidoReserva item : itens){
                Optional<ProdutoEntity> produto = produtoRepository.findById(item.produtoId());

                if(produto.isEmpty() || produto.get().getQuantidadeEmEstoque() < item.quantidade()) {
                    return new EstoqueRespostaJson(idPedido, false);

                }else{
                    int novoEstoque = produto.get().getQuantidadeEmEstoque() - item.quantidade();
                    produto.get().setQuantidadeEmEstoque(novoEstoque);

                    produtoList.add(produto.get());
                }
            }
        }catch (Exception e){
            throw new ErroAoAcessarRepositorioException();
        }

        atualizarProdutosEmLote(produtoList);

        return new EstoqueRespostaJson(idPedido, true);
    }

    @Override
    public List<ProdutoJson> consultarProdutos() {
        try{
            List<ProdutoEntity> produtos = produtoRepository.findAll();

            return mapToProdutoJson(produtos);

        }catch (Exception e){
            throw new ErroAoAcessarRepositorioException();
        }
    }

    private List<ProdutoEntity> mapToEntity(List<ProdutoBatch> produto){
        UUID uuid = UUID.randomUUID();
        long numeroAleatorio = uuid.getMostSignificantBits() & Long.MAX_VALUE;

        return produto.stream().map(prod -> new ProdutoEntity(
                numeroAleatorio,
                prod.getNome(),
                prod.getDescricao(),
                new BigDecimal(prod.getPreco()),
                prod.getQuantidadeEmEstoque())
        ).toList();
    }

    private List<ProdutoJson> mapToProdutoJson(List<ProdutoEntity> produtosEntity){
        return produtosEntity.stream()
                .map(produtoEntity -> new ProdutoJson(
                        produtoEntity.getId(),
                        produtoEntity.getNome(),
                        produtoEntity.getDescricao(),
                        produtoEntity.getPreco(),
                        produtoEntity.getQuantidadeEmEstoque()
        )).toList();
    }
}
