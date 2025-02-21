package br.com.catalogo.produtos.controller.api;

import br.com.catalogo.produtos.controller.api.json.ProdutoJson;
import br.com.catalogo.produtos.domain.ProdutoBatch;
import br.com.catalogo.produtos.gateway.api.json.RegistrarRespostaJson;
import br.com.catalogo.produtos.infraestrutura.GlobalExceptionHandler;
import br.com.catalogo.produtos.usecase.ProdutoUseCase;
import br.com.catalogo.produtos.utils.ProdutoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProdutoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProdutoUseCase produtoUseCase;

    @Mock
    private Job processarProdutos;

    @Mock
    private JobLauncher jobLauncher;

    AutoCloseable mock;

    @BeforeEach
    void setup(){
        mock = MockitoAnnotations.openMocks(this);
        ProdutoController produtoController = new ProdutoController(produtoUseCase, jobLauncher, processarProdutos);
        mockMvc = MockMvcBuilders.standaloneSetup(produtoController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                })
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        mock.close();
    }

    @Test
    void deveRegistrarProdutosEmLoteComSucesso() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "produtos.csv",
                "text/csv",
                "Arquivo com produtos".getBytes()
        );

        List<ProdutoBatch> produtos = ProdutoHelper.gerarListaProdutoBatch();
        RegistrarRespostaJson respostaEsperada = new RegistrarRespostaJson(true);

        JobExecution jobExecution = Mockito.mock(JobExecution.class);
        ExecutionContext executionContext = Mockito.mock(ExecutionContext.class);

        when(jobLauncher.run(any(), any())).thenReturn(jobExecution);
        when(jobExecution.getExecutionContext()).thenReturn(executionContext);
        when(executionContext.get("produtos")).thenReturn(produtos);
        when(produtoUseCase.registrarProdutosEmLote(anyList())).thenReturn(respostaEsperada);

        //Act & Assert
        mockMvc.perform(multipart("/produtos/importar").file(file))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.produtosRegistrados").value(true));
    }

    @Test
    void deveLancarNenhumProdutoInformadoExceptionAoRegistrarProdutosEmLote() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "produtos.csv",
                "text/csv", "Arquivo sem produtos".getBytes()
        );

        JobExecution jobExecution = Mockito.mock(JobExecution.class);
        ExecutionContext executionContext = Mockito.mock(ExecutionContext.class);

        when(jobLauncher.run(any(), any())).thenReturn(jobExecution);
        when(jobExecution.getExecutionContext()).thenReturn(executionContext);
        when(executionContext.get("produtos")).thenReturn(Collections.emptyList());

        //Act & Assert
        mockMvc.perform(multipart("/produtos/importar").file(file))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.code").value("produtos.nenhumProdutoInformado"))
                .andExpect(jsonPath("$.message").value("Nenhum produto informado."));
    }

    @Test
    void deveConsultarProdutosComSucesso() throws Exception {
        //Arrange
        List<ProdutoJson> produtos = ProdutoHelper.gerarListaProdutoJson();
        Mockito.doReturn(produtos).when(produtoUseCase).consultarProdutos();

        //Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos/consultar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(produtos.getFirst().getId()))
                .andExpect(jsonPath("$[0].nome").value(produtos.getFirst().getNome()))
                .andExpect(jsonPath("$[0].descricao").value(produtos.getFirst().getDescricao()))
                .andExpect(jsonPath("$[0].preco").value(produtos.getFirst().getPreco()))
                .andExpect(jsonPath("$[0].quantidadeEmEstoque").value(produtos.getFirst().getQuantidadeEmEstoque()))
                .andExpect(jsonPath("$[1].id").value(produtos.get(1).getId()))
                .andExpect(jsonPath("$[1].nome").value(produtos.get(1).getNome()))
                .andExpect(jsonPath("$[1].descricao").value(produtos.get(1).getDescricao()))
                .andExpect(jsonPath("$[1].preco").value(produtos.get(1).getPreco()))
                .andExpect(jsonPath("$[1].quantidadeEmEstoque").value(produtos.get(1).getQuantidadeEmEstoque()));
    }
}
