package br.com.catalogo.produtos.config;

import br.com.catalogo.produtos.domain.Produto;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class ProdutoItemWriter implements ItemWriter<Produto>, ChunkListener {

    private final List<Produto> produtos = new ArrayList<>();

    @Override
    public void write(Chunk<? extends Produto> chunk) throws Exception {
        List<Produto> items = new ArrayList<>();

        for (Produto produto : chunk) {
            items.add(produto);
        }

        produtos.addAll(items);
    }

    public void afterJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("produtos", produtos);
    }
}
