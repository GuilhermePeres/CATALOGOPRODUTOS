package br.com.catalogo.produtos.utils;

import br.com.catalogo.produtos.domain.Produto;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProdutoHelper {

    public static List<ProdutoEntity> gerarListaProdutoEntity(){
        return List.of(
                new ProdutoEntity(
                        UUID.randomUUID(),
                        "Formador de Heróis",
                        "5 Práticas Essenciais Para Líderes Multiplicarem Líderes...",
                        BigDecimal.valueOf(50.59),
                        20
                ),
                new ProdutoEntity(
                        UUID.randomUUID(),
                        "Lidere com isso em mente",
                        "Lidere com isso em mente apresenta percepções transformadoras de Groeschel",
                        BigDecimal.valueOf(49.46),
                        20)
        );
    }

    public static List<Produto> gerarListaProduto(){
        return List.of(
                new Produto(
                        "Formador de Heróis",
                        "5 Práticas Essenciais Para Líderes Multiplicarem Líderes...",
                        BigDecimal.valueOf(50.59),
                        20
                ),
                new Produto(
                        "Lidere com isso em mente",
                        "Lidere com isso em mente apresenta percepções transformadoras de Groeschel",
                        BigDecimal.valueOf(49.46),
                        20)
        );
    }
}
