package br.com.catalogo.produtos.usecase.rule;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.exception.PrecoMenorIgualAZeroException;
import br.com.catalogo.produtos.usecase.rule.dto.InputDto;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PrecoMenorIgualAZeroRuleTest {

    @InjectMocks
    private PrecoMenorIgualAZeroRule precoMenorIgualAZeroRule;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

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
