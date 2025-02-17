package br.com.catalogo.produtos.config;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

public class MillisecondIncrementer implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        long currentTimeMillis = System.currentTimeMillis();
        return new JobParametersBuilder()
                .addLong("timestamp", currentTimeMillis)
                .toJobParameters();
    }
}
