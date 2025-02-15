package br.com.catalogo.produtos.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoJobCompletionListener implements JobExecutionListener {

    private final ProdutoItemWriter produtoWriter;

    public ProdutoJobCompletionListener(ProdutoItemWriter produtoWriter) {
        this.produtoWriter = produtoWriter;
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        produtoWriter.afterJob(jobExecution);
    }
}
