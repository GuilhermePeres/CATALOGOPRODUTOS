package br.com.catalogo.produtos.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuantidadeMenorQueZeroExceptionTest {

    @Test
    void deveRetornarCodigoCorreto() {
        QuantidadeMenorQueZeroException exception = new QuantidadeMenorQueZeroException();
        assertThat(exception.getCode()).isEqualTo("produtos.quantidadeMenorQueZero");
    }

    @Test
    void deveRetornarMensagemCorreta() {
        QuantidadeMenorQueZeroException exception = new QuantidadeMenorQueZeroException();
        assertThat(exception.getMessage()).isEqualTo("Quantidade em estoque está menor que zero.");
    }

    @Test
    void deveRetornarHttpStatusCorreto() {
        QuantidadeMenorQueZeroException exception = new QuantidadeMenorQueZeroException();
        assertThat(exception.getHttpStatus()).isEqualTo(422);
    }
}
