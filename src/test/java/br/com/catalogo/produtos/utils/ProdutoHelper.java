package br.com.catalogo.produtos.utils;

import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.database.jpa.entity.ProdutoEntity;
import lombok.val;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.cs.ISO_8859_1;
import sun.nio.cs.US_ASCII;
import sun.nio.cs.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
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

    public static String gerarArquivoCsv(List<ProdutoBatch> produtos){
        return produtos.getFirst().getNome() + ","
                + produtos.getFirst().getDescricao() + ","
                + produtos.getFirst().getPreco() + ","
                + produtos.getFirst().getQuantidadeEmEstoque() + "\n"
                + produtos.get(1).getNome() + ","
                + produtos.get(1).getDescricao() + ","
                + produtos.get(1).getPreco() + ","
                + produtos.get(1).getQuantidadeEmEstoque();
    }
}
