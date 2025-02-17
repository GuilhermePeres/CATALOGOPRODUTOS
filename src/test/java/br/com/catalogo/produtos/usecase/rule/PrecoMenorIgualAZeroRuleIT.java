package br.com.catalogo.produtos.usecase.rule;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.exception.PrecoMenorIgualAZeroException;
import br.com.catalogo.produtos.usecase.rule.dto.InputDto;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class PrecoMenorIgualAZeroRuleIT {
    @Autowired
    private PrecoMenorIgualAZeroRule precoMenorIgualAZeroRule;

    @Test
    void deveValidarComSucesso() {
        //Arrange
        List<ProdutoBatch> produtoBatch = ProdutoHelper.gerarListaProdutoBatch();
        InputDto inputDto = new InputDto(produtoBatch.getFirst());

        //Act
        precoMenorIgualAZeroRule.validate(inputDto);
    }

    @Test
    void deveLancarErroPrecoMenorIgualAZeroExceptionAoValidar() {
        //Arrange
        List<ProdutoBatch> produtoBatch = ProdutoHelper.gerarListaProdutoBatch();
        produtoBatch.getFirst().setPreco("0");

        InputDto inputDto = new InputDto(produtoBatch.getFirst());

        //Act
        assertThatThrownBy(() -> precoMenorIgualAZeroRule.validate(inputDto))
                .isInstanceOf(PrecoMenorIgualAZeroException.class);
    }
}
