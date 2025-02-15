package br.com.catalogo.produtos.usecase.rule;

import br.com.catalogo.produtos.domain.Produto;
import br.com.catalogo.produtos.exception.QuantidadeEmEstoqueMenorOuIgualAZeroException;
import br.com.catalogo.produtos.usecase.rule.dto.InputDto;
import org.springframework.stereotype.Component;

@Component
public class QuantidadeMenorOuIgualAZeroRule implements RuleBase{

    @Override
    public void validate(InputDto inputDto) {
        Produto novoProduto = inputDto.novoProduto();

        if(novoProduto.quantidadeEmEstoqueMenorQueZero()){
            throw new QuantidadeEmEstoqueMenorOuIgualAZeroException();
        }
    }
}
