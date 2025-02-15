package br.com.catalogo.produtos.config;

import br.com.catalogo.produtos.controller.json.ProdutoJson;
import br.com.catalogo.produtos.domain.Produto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ProdutoBatchConfig {

    @Bean
    public Job processarProdutos(JobRepository jobRepository, Step step, ProdutoJobCompletionListener listener) {
        return new JobBuilder("processarProdutos", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step)
                .build();
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager,
                     ItemReader<ProdutoJson> itemReader,
                     ItemWriter<Produto> itemWriter,
                     ItemProcessor<ProdutoJson, Produto> itemProcessor) {
        return new StepBuilder("step", jobRepository)
                .<ProdutoJson, Produto>chunk(5, platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<ProdutoJson> itemReader(@Value("#{jobParameters['filePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<ProdutoJson>()
                .name("produtoJsonItemReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names("nome", "descricao", "preco", "quantidadeEmEstoque")
                .fieldSetMapper(new ProdutoJsonFieldSetMapper())
                .build();
    }

    @Bean
    public ItemProcessor<ProdutoJson, Produto> itemProcessor() {
        return json -> new Produto(
                    json.getNome(),
                    json.getDescricao(),
                    json.getPreco(),
                    json.getQuantidadeEmEstoque()
        );
    }

    @Bean
    public ItemWriter<Produto> itemWriter(){
        return new ProdutoItemWriter();
    }

    @Bean
    public ProdutoJobCompletionListener produtoJobCompletionListener(ProdutoItemWriter produtoWriter) {
        return new ProdutoJobCompletionListener(produtoWriter);
    }
}
