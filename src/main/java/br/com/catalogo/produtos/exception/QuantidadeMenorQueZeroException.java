package br.com.catalogo.produtos.exception;

import lombok.Getter;

@Getter
public class QuantidadeMenorQueZeroException extends RuntimeException{
    private static final String CODE = "produtos.quantidadeMenorQueZero";
    private static final String MESSAGE = "Quantidade em estoque está menor que zero.";
    private static final Integer HTTPSTATUS = 422;

    public int getHttpStatus() {
        return HTTPSTATUS;
    }

    public String getCode(){
        return CODE;
    }

    @Override
    public String getMessage() { return MESSAGE; }
}
