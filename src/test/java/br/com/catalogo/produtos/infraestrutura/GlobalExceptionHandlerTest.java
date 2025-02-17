package br.com.catalogo.produtos.infraestrutura;

import br.com.catalogo.produtos.controller.api.json.ExceptionJson;
import br.com.catalogo.produtos.exception.ErroAoAcessarRepositorioException;
import br.com.catalogo.produtos.exception.NenhumProdutoInformadoException;
import br.com.catalogo.produtos.exception.PrecoMenorIgualAZeroException;
import br.com.catalogo.produtos.exception.QuantidadeMenorQueZeroException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private ErroAoAcessarRepositorioException erroAoAcessarRepositorioException;

    @Mock
    private PrecoMenorIgualAZeroException precoMenorIgualAZeroException;

    @Mock
    private QuantidadeMenorQueZeroException quantidadeMenorQueZeroException;

    @Mock
    private NenhumProdutoInformadoException nenhumProdutoInformadoException;

    AutoCloseable openMocks;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void deveTratarErroAoAcessarRepositorioException() {
        //Arrange
        String codigoEsperado = "produtos.erroAcessarRepositorio";
        String mensagemEsperada = "Erro ao acessar repositório.";
        HttpStatus statusEsperado = HttpStatus.INTERNAL_SERVER_ERROR;

        when(erroAoAcessarRepositorioException.getCode()).thenReturn(codigoEsperado);
        when(erroAoAcessarRepositorioException.getMessage()).thenReturn(mensagemEsperada);
        when(erroAoAcessarRepositorioException.getHttpStatus()).thenReturn(statusEsperado.value());

        //Act
        ResponseEntity<ExceptionJson> response = globalExceptionHandler.tratarErroAoAcessarRepositorioException(erroAoAcessarRepositorioException);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(statusEsperado);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(codigoEsperado);
        assertThat(response.getBody().message()).isEqualTo(mensagemEsperada);
        assertThat(response.getHeaders()).isEqualTo(new HttpHeaders());
    }

    @Test
    void deveTratarPrecoMenorIgualAZeroException() {
        //Arrange
        String codigoEsperado = "produtos.precoMenorIgualAZero";
        String mensagemEsperada = "Preço está menor ou igual a zero.";
        HttpStatus statusEsperado = HttpStatus.UNPROCESSABLE_ENTITY;

        when(precoMenorIgualAZeroException.getCode()).thenReturn(codigoEsperado);
        when(precoMenorIgualAZeroException.getMessage()).thenReturn(mensagemEsperada);
        when(precoMenorIgualAZeroException.getHttpStatus()).thenReturn(statusEsperado.value());

        //Act
        ResponseEntity<ExceptionJson> response = globalExceptionHandler.tratarPrecoMenorIgualAZeroException(precoMenorIgualAZeroException);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(statusEsperado);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(codigoEsperado);
        assertThat(response.getBody().message()).isEqualTo(mensagemEsperada);
        assertThat(response.getHeaders()).isEqualTo(new HttpHeaders());
    }

    @Test
    void deveTratarQuantidadeEmEstoqueMenorQueZeroException() {
        //Arrange
        String codigoEsperado = "produtos.quantidadeEmEstoqueMenorOuIgualAZero";
        String mensagemEsperada = "Quantidade em estoque está menor ou igual a zero.";
        HttpStatus statusEsperado = HttpStatus.UNPROCESSABLE_ENTITY;

        when(quantidadeMenorQueZeroException.getCode()).thenReturn(codigoEsperado);
        when(quantidadeMenorQueZeroException.getMessage()).thenReturn(mensagemEsperada);
        when(quantidadeMenorQueZeroException.getHttpStatus()).thenReturn(statusEsperado.value());

        //Act
        ResponseEntity<ExceptionJson> response = globalExceptionHandler.tratarQuantidadeEmEstoqueMenorQueZeroException(quantidadeMenorQueZeroException);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(statusEsperado);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(codigoEsperado);
        assertThat(response.getBody().message()).isEqualTo(mensagemEsperada);
        assertThat(response.getHeaders()).isEqualTo(new HttpHeaders());
    }

    @Test
    void deveTratarNenhumProdutoInformadoException() {
        //Arrange
        String codigoEsperado = "produtos.nenhumProdutoInformado";
        String mensagemEsperada = "Nenhum produto informado.";
        HttpStatus statusEsperado = HttpStatus.UNPROCESSABLE_ENTITY;

        when(nenhumProdutoInformadoException.getCode()).thenReturn(codigoEsperado);
        when(nenhumProdutoInformadoException.getMessage()).thenReturn(mensagemEsperada);
        when(nenhumProdutoInformadoException.getHttpStatus()).thenReturn(statusEsperado.value());

        //Act
        ResponseEntity<ExceptionJson> response = globalExceptionHandler.tratarNenhumProdutoInformadoException(nenhumProdutoInformadoException);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(statusEsperado);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().code()).isEqualTo(codigoEsperado);
        assertThat(response.getBody().message()).isEqualTo(mensagemEsperada);
        assertThat(response.getHeaders()).isEqualTo(new HttpHeaders());
    }
}
