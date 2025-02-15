package br.com.catalogo.produtos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoBatch {
    private String nome;
    private String descricao;
    private String preco;
    private int quantidadeEmEstoque;

    public boolean quantidadeEmEstoqueMenorQueZero(){ return quantidadeEmEstoque < 0; }
    public boolean precoMenorIgualAZero(){ return new BigDecimal(this.getPreco()).compareTo(BigDecimal.ZERO) <= 0; }
}
