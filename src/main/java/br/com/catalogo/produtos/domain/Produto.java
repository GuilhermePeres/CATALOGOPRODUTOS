package br.com.catalogo.produtos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidadeEmEstoque;

    public boolean precoMenorIgualAZero(){ return this.getPreco().compareTo(BigDecimal.ZERO) <= 0; }
    public boolean quantidadeEmEstoqueMenorQueZero(){ return quantidadeEmEstoque < 0; }
}
