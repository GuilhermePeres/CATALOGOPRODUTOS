package br.com.catalogo.produtos.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NenhumProdutoInformadoExceptionTest {

    @Test
    void deveRetornarCodigoCorreto() {
        NenhumProdutoInformadoException exception = new NenhumProdutoInformadoException();
        assertThat(exception.getCode()).isEqualTo("produtos.nenhumProdutoInformado");
    }

    @Test
    void deveRetornarMensagemCorreta() {
        NenhumProdutoInformadoException exception = new NenhumProdutoInformadoException();
        assertThat(exception.getMessage()).isEqualTo("Nenhum produto informado.");
    }

    @Test
    void deveRetornarHttpStatusCorreto() {
        NenhumProdutoInformadoException exception = new NenhumProdutoInformadoException();
        assertThat(exception.getHttpStatus()).isEqualTo(422);
    }
}
