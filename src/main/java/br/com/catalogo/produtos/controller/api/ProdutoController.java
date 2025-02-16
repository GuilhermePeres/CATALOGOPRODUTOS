package br.com.catalogo.produtos.controller.api;

import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.exception.NenhumProdutoInformadoException;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.usecase.ProdutoUseCase;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final JobLauncher jobLauncher;
    private final Job processarProdutos;
    private final ProdutoUseCase produtoUseCase;

    public ProdutoController(ProdutoUseCase produtoUseCase, JobLauncher jobLauncher, Job processarProdutos) {
        this.produtoUseCase = produtoUseCase;
        this.jobLauncher = jobLauncher;
        this.processarProdutos = processarProdutos;
    }

    @PostMapping("/importar")
    public RegistrarRespostaJson registrarProdutosEmLote(@RequestParam("file") MultipartFile file) throws IOException,
            JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        File tempDir = Files.createTempDirectory("produtos-temp").toFile();
        File fileToImport;

        try {
            fileToImport = new File(tempDir, Objects.requireNonNull(file.getOriginalFilename()));

        }catch (Exception e){
            throw new NenhumProdutoInformadoException();
        }

        file.transferTo(fileToImport);

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filePath", fileToImport.getAbsolutePath())
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(processarProdutos, jobParameters);

        List<?> listProdutos = (List<?>) jobExecution.getExecutionContext().get("produtos");

        cleanUp(fileToImport);

        if (listProdutos != null && !listProdutos.isEmpty() && listProdutos.getFirst() instanceof ProdutoBatch) {
            List<ProdutoBatch> produtos = new ArrayList<>();

            for (Object produto : listProdutos) {
                produtos.add((ProdutoBatch) produto);
            }

            return produtoUseCase.registrarProdutosEmLote(produtos);
        }

        throw new NenhumProdutoInformadoException();
    }

    private void cleanUp(File fileToImport) throws IOException {
        Files.delete(fileToImport.toPath());
    }

    @GetMapping("/consultar")
    public List<ProdutoJson> consultarProdutos() {
        return produtoUseCase.consultarProdutos();
    }

}