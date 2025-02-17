package br.com.catalogo.produtos.usecase.rule;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.exception.QuantidadeMenorQueZeroException;
import br.com.catalogo.produtos.usecase.rule.dto.InputDto;
import org.springframework.stereotype.Component;

@Component
public class QuantidadeMenorOuIgualAZeroRule implements RuleBase{

    @Override
    public void validate(InputDto inputDto) {
        ProdutoBatch novoProduto = inputDto.novoProduto();

        if(novoProduto.quantidadeEmEstoqueMenorQueZero()){
            throw new QuantidadeMenorQueZeroException();
        }
    }
}
