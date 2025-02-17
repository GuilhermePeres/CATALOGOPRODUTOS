package br.com.catalogo.produtos.infraestrutura;

import br.com.catalogo.produtos.controller.api.json.ExceptionJson;
import br.com.catalogo.produtos.exception.ErroAoAcessarRepositorioException;
import br.com.catalogo.produtos.exception.NenhumProdutoInformadoException;
import br.com.catalogo.produtos.exception.PrecoMenorIgualAZeroException;
import br.com.catalogo.produtos.exception.QuantidadeMenorQueZeroException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ErroAoAcessarRepositorioException.class)
    public ResponseEntity<ExceptionJson> tratarErroAoAcessarRepositorioException(ErroAoAcessarRepositorioException ex) {
        final ExceptionJson exceptionJson = new ExceptionJson(ex.getCode(), ex.getMessage());

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(exceptionJson, new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(PrecoMenorIgualAZeroException.class)
    public ResponseEntity<ExceptionJson> tratarPrecoMenorIgualAZeroException(PrecoMenorIgualAZeroException ex) {
        final ExceptionJson exceptionJson = new ExceptionJson(ex.getCode(), ex.getMessage());

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(exceptionJson, new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(QuantidadeMenorQueZeroException.class)
    public ResponseEntity<ExceptionJson> tratarQuantidadeEmEstoqueMenorQueZeroException(QuantidadeMenorQueZeroException ex) {
        final ExceptionJson exceptionJson = new ExceptionJson(ex.getCode(), ex.getMessage());

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(exceptionJson, new HttpHeaders(), ex.getHttpStatus());
    }

    @ExceptionHandler(NenhumProdutoInformadoException.class)
    public ResponseEntity<ExceptionJson> tratarNenhumProdutoInformadoException(NenhumProdutoInformadoException ex) {
        final ExceptionJson exceptionJson = new ExceptionJson(ex.getCode(), ex.getMessage());

        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(exceptionJson, new HttpHeaders(), ex.getHttpStatus());
    }
}
