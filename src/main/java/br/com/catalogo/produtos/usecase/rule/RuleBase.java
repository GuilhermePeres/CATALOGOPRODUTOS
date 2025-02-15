package br.com.catalogo.produtos.usecase.rule;

import br.com.catalogo.produtos.usecase.rule.dto.InputDto;

public interface RuleBase {
    void validate(InputDto inputDto);
}
