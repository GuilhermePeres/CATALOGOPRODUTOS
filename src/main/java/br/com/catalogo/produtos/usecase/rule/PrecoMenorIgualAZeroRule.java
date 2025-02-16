package br.com.catalogo.produtos.usecase.rule;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.exception.PrecoMenorIgualAZeroException;
import br.com.catalogo.produtos.usecase.rule.dto.InputDto;
import org.springframework.stereotype.Component;

@Component
public class PrecoMenorIgualAZeroRule implements RuleBase{

    @Override
    public void validate(InputDto inputDto) {
        ProdutoBatch novoProduto = inputDto.novoProduto();

        if(novoProduto.precoMenorIgualAZero()){
            throw new PrecoMenorIgualAZeroException();
        }
    }
}
