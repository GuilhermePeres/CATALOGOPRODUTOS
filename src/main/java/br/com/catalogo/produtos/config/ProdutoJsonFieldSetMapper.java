package br.com.catalogo.produtos.config;

import br.com.catalogo.produtos.controller.json.ProdutoJson;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.util.UUID;

public class ProdutoJsonFieldSetMapper implements FieldSetMapper<ProdutoJson> {
    @Override
    public ProdutoJson mapFieldSet(FieldSet fieldSet) {
        long numeroAleatorio = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        return new ProdutoJson(
                numeroAleatorio,
                fieldSet.readString("nome"),
                fieldSet.readString("descricao"),
                fieldSet.readBigDecimal("preco"),
                fieldSet.readInt("quantidadeEmEstoque")
        );
    }
}