package br.com.catalogo.produtos.exception;

import lombok.Getter;

@Getter
public class PrecoMenorIgualAZeroException extends RuntimeException{
    private static final String CODE = "produtos.precoMenorIgualAZero";
    private static final String MESSAGE = "Preço está menor ou igual a zero.";
    private static final Integer HTTPSTATUS = 422;

    public int getHttpStatus() {
        return HTTPSTATUS;
    }

    public String getCode(){
        return CODE;
    }
}
