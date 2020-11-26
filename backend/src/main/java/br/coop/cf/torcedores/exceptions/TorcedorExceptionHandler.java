package br.coop.cf.torcedores.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class TorcedorExceptionHandler {

    @ResponseBody
    @ExceptionHandler(TorcedorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String torcedorNotFoundHandler(TorcedorNotFoundException ex) {
        log.error( ex.getMessage(), ex );
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String torcedorException(Exception ex) {
        log.error( ex.getMessage(), ex );
        return ex.getMessage();
    }

}
