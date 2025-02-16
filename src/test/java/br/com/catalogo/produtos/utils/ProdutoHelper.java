package br.com.catalogo.produtos.utils;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;

public class ProdutoHelper {

    public static List<ProdutoEntity> gerarListaProdutoEntity(){
        SecureRandom random = new SecureRandom();
        long numeroAleatorio = random.nextLong();

        return List.of(
                new ProdutoEntity(
                        numeroAleatorio,
                        "Formador de Heróis",
                        "5 Práticas Essenciais Para Líderes Multiplicarem Líderes...",
                        BigDecimal.valueOf(50.59),
                        20
                ),
                new ProdutoEntity(
                        numeroAleatorio,
                        "Lidere com isso em mente",
                        "Lidere com isso em mente apresenta percepções transformadoras de Groeschel",
                        BigDecimal.valueOf(49.46),
                        20)
        );
    }

    public static List<ProdutoBatch> gerarListaProduto(){
        return List.of(
                new ProdutoBatch(
                        "Formador de Heróis",
                        "5 Práticas Essenciais Para Líderes Multiplicarem Líderes...",
                        "50.59",
                        20
                ),
                new ProdutoBatch(
                        "Lidere com isso em mente",
                        "Lidere com isso em mente apresenta percepções transformadoras de Groeschel",
                        "49.46",
                        20)
        );
    }
}
