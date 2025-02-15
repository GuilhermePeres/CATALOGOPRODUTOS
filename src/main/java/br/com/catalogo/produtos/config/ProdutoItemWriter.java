package br.com.catalogo.produtos.config;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class ProdutoItemWriter implements ItemWriter<ProdutoBatch>, ChunkListener {

    private final List<ProdutoBatch> produtos = new ArrayList<>();

    @Override
    public void write(Chunk<? extends ProdutoBatch> chunk) throws Exception {
        List<ProdutoBatch> items = new ArrayList<>();

        for (ProdutoBatch produto : chunk) {
            items.add(produto);
        }

        produtos.addAll(items);
    }

    public void afterJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("produtos", produtos);
    }
}
