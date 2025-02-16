package br.com.catalogo.produtos.jpa.repository;

import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import br.com.catalogo.produtos.gateway.database.jpa.repository.ProdutoRepository;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class ProdutoRepositoryIT {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    void devePermitirCriarTabela(){
        var totalRegistros = produtoRepository.count();

        assertThat(totalRegistros).isEqualTo(3);
    }

    @Test
    void devePermitirSalvarListaDeProdutos() {
        //Arrange
        List<ProdutoEntity> produtos = ProdutoHelper.gerarListaProdutoEntity();

        //Act
        List<ProdutoEntity> produtosSalvos = produtoRepository.saveAll(produtos);

        //Assert
        assertThat(produtosSalvos).hasSize(produtos.size());

        for(int i=0; i<produtos.size(); i++){
            assertThat(produtosSalvos.get(i).getId()).isEqualTo(produtos.get(i).getId());
            assertThat(produtosSalvos.get(i).getNome()).isEqualTo(produtos.get(i).getNome());
            assertThat(produtosSalvos.get(i).getDescricao()).isEqualTo(produtos.get(i).getDescricao());
            assertThat(produtosSalvos.get(i).getPreco()).isEqualTo(produtos.get(i).getPreco());
            assertThat(produtosSalvos.get(i).getQuantidadeEmEstoque()).isEqualTo(produtos.get(i).getQuantidadeEmEstoque());
        }
    }

    @Test
    void devePermitirEncontrarProdutoPorId() {
        //Arrange
        Long id = 1L;

        //Act
        Optional<ProdutoEntity> produtoEncontrado = produtoRepository.findById(id);

        //Assert
        assertThat(produtoEncontrado).isPresent();
        assertThat(produtoEncontrado.get().getId()).isEqualTo(id);
    }

    @Test
    void devePermitirEncontrarTodosOsProdutos() {
        //Arrange
        Long id0 = 1L;
        Long id1 = 2L;
        Long id2 = 3L;

        //Act
        List<ProdutoEntity> produtosRetornados = produtoRepository.findAll();

        //Assert
        assertThat(produtosRetornados).hasSize(3);
        assertThat(produtosRetornados.getFirst().getId()).isEqualTo(id0);
        assertThat(produtosRetornados.get(1).getId()).isEqualTo(id1);
        assertThat(produtosRetornados.get(2).getId()).isEqualTo(id2);
    }
}