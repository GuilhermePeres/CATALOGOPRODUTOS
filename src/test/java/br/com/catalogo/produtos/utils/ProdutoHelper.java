package br.com.catalogo.produtos.utils;

import br.com.catalogo.produtos.controller.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProdutoHelper {

    public static List<ProdutoEntity> gerarListaProdutoEntity(){
        long numeroAleatorio = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long numeroAleatorio2 = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        return List.of(
                new ProdutoEntity(
                        numeroAleatorio,
                        "Formador de Heróis",
                        "5 Práticas Essenciais Para Líderes Multiplicarem Líderes...",
                        BigDecimal.valueOf(50.59),
                        20
                ),
                new ProdutoEntity(
                        numeroAleatorio2,
                        "Lidere com isso em mente",
                        "Lidere com isso em mente apresenta percepções transformadoras de Groeschel",
                        BigDecimal.valueOf(49.46),
                        20)
        );
    }

    public static List<ProdutoBatch> gerarListaProdutoBatch(){
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

    public static List<ItemPedidoReserva> gerarListaItemPedidoReservaAleatorio() {
        long numeroAleatorio = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long numeroAleatorio2 = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        return List.of(
                new ItemPedidoReserva(numeroAleatorio,10),
                new ItemPedidoReserva(numeroAleatorio2,10)
        );
    }

    public static List<ProdutoJson> gerarListaProdutoJson(){
        long numeroAleatorio = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
        long numeroAleatorio2 = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        return List.of(
                new ProdutoJson(
                        numeroAleatorio,
                        "Formador de Heróis",
                        "5 Práticas Essenciais Para Líderes Multiplicarem Líderes...",
                        BigDecimal.valueOf(50.59),
                        20
                ),
                new ProdutoJson(
                        numeroAleatorio2,
                        "Lidere com isso em mente",
                        "Lidere com isso em mente apresenta percepções transformadoras de Groeschel",
                        BigDecimal.valueOf(49.46),
                        20)
        );
    }
}
