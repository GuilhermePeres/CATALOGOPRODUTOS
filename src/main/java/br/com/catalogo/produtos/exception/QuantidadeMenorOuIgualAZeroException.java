package br.com.catalogo.produtos.exception;

import lombok.Getter;

@Getter
public class QuantidadeMenorOuIgualAZeroException extends RuntimeException{
    private static final String CODE = "produtos.quantidadeEmEstoqueMenorOuIgualAZero";
    private static final String MESSAGE = "Quantidade em estoque está menor ou igual a zero.";
    private static final Integer HTTPSTATUS = 422;

    public int getHttpStatus() {
        return HTTPSTATUS;
    }

    public String getCode(){
        return CODE;
    }
}
