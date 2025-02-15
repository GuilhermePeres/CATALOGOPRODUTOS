package br.com.catalogo.produtos.controller;

import br.com.catalogo.produtos.controller.json.ItemPedidoReservaJson;
import br.com.catalogo.produtos.controller.json.ProdutoJson;
import br.com.catalogo.produtos.controller.json.ReservaProdutoJson;
import br.com.catalogo.produtos.domain.ItemPedidoReserva;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.domain.ReservaProduto;
import br.com.catalogo.produtos.exception.NenhumProdutoInformadoException;
import br.com.catalogo.produtos.gateway.api.json.EstoqueRespostaJson;
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

    @PutMapping
    public EstoqueRespostaJson atualizarProdutosPorPedido(@RequestBody ReservaProdutoJson reservaProdutoJson){
        ReservaProduto reservaProduto = mapToDomain(reservaProdutoJson);

        return produtoUseCase.atualizarProdutosPorPedido(reservaProduto.getPedidoId(),reservaProduto.getItens());
    }

    private ReservaProduto mapToDomain(ReservaProdutoJson reservaProdutoJson){
        List<ItemPedidoReserva> itensPedidoReserva = new ArrayList<>();

        for (ItemPedidoReservaJson itemJson : reservaProdutoJson.getItens()) {
            ItemPedidoReserva itemPedidoReserva = new ItemPedidoReserva(
                    itemJson.getProdutoId(),
                    itemJson.getQuantidade()
            );
            itensPedidoReserva.add(itemPedidoReserva);
        }

        return new ReservaProduto(
                reservaProdutoJson.getPedidoId(),
                reservaProdutoJson.getClienteId(),
                itensPedidoReserva
        );
    }
}