package br.com.catalogo.produtos.exception;

import lombok.Getter;

@Getter
public class ErroAoAcessarRepositorioException extends RuntimeException{
    private static final String CODE = "produtos.erroAcessarRepositorio";
    private static final String MESSAGE = "Erro ao acessar repositório.";
    private static final Integer HTTPSTATUS = 500;

    public int getHttpStatus() {
        return HTTPSTATUS;
    }

    public String getCode(){
        return CODE;
    }
}
