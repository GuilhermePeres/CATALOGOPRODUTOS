package br.com.catalogo.produtos.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PrecoMenorIgualAZeroExceptionTest {

    @Test
    void deveRetornarCodigoCorreto() {
        PrecoMenorIgualAZeroException exception = new PrecoMenorIgualAZeroException();
        assertThat(exception.getCode()).isEqualTo("produtos.precoMenorIgualAZero");
    }

    @Test
    void deveRetornarMensagemCorreta() {
        PrecoMenorIgualAZeroException exception = new PrecoMenorIgualAZeroException();
        assertThat(exception.getMessage()).isEqualTo("Preço está menor ou igual a zero.");
    }

    @Test
    void deveRetornarHttpStatusCorreto() {
        PrecoMenorIgualAZeroException exception = new PrecoMenorIgualAZeroException();
        assertThat(exception.getHttpStatus()).isEqualTo(422);
    }
}
