package br.com.catalogo.produtos.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErroAoAcessarRepositorioExceptionTest {

    @Test
    void deveRetornarCodigoCorreto() {
        ErroAoAcessarRepositorioException exception = new ErroAoAcessarRepositorioException();
        assertThat(exception.getCode()).isEqualTo("produtos.erroAcessarRepositorio");
    }

    @Test
    void deveRetornarMensagemCorreta() {
        ErroAoAcessarRepositorioException exception = new ErroAoAcessarRepositorioException();
        assertThat(exception.getMessage()).isEqualTo("Erro ao acessar repositório.");
    }

    @Test
    void deveRetornarHttpStatusCorreto() {
        ErroAoAcessarRepositorioException exception = new ErroAoAcessarRepositorioException();
        assertThat(exception.getHttpStatus()).isEqualTo(500);
    }
}
