package br.com.catalogo.produtos.config;

import br.com.catalogo.produtos.domain.ProdutoBatch;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
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
                     ItemReader<ProdutoBatch> itemReader,
                     ItemWriter<ProdutoBatch> itemWriter) {
        return new StepBuilder("step", jobRepository)
                .<ProdutoBatch, ProdutoBatch>chunk(5, platformTransactionManager)
                .reader(itemReader)
                .writer(itemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<ProdutoBatch> itemReader(@Value("#{jobParameters['filePath']}") String filePath) {
        BeanWrapperFieldSetMapper<ProdutoBatch> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProdutoBatch.class);

        return new FlatFileItemReaderBuilder<ProdutoBatch>()
                .name("produtoItemReader")
                .resource(new ClassPathResource(filePath))
                .delimited().delimiter(",")
                .names("nome", "descricao", "preco", "quantidadeEmEstoque")
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    @Bean
    public ItemWriter<ProdutoBatch> itemWriter(){
        return new ProdutoItemWriter();
    }

    @Bean
    public ProdutoJobCompletionListener produtoJobCompletionListener(ProdutoItemWriter produtoWriter) {
        return new ProdutoJobCompletionListener(produtoWriter);
    }
}
