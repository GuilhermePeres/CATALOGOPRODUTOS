package br.com.catalogo.produtos.exception;

import lombok.Getter;

@Getter
public class NenhumProdutoInformadoException extends RuntimeException{
    private static final String CODE = "produtos.nenhumProdutoInformado";
    private static final String MESSAGE = "Nenhum produto informado.";
    private static final Integer HTTPSTATUS = 422;

    public int getHttpStatus() {
        return HTTPSTATUS;
    }

    public String getCode(){
        return CODE;
    }
}
