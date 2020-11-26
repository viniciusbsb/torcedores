package br.coop.cf.torcedores.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class TorcedorExceptionHandler {

    @ResponseBody
    @ExceptionHandler(TorcedorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Map<String, Object> torcedorNotFoundHandler(TorcedorNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return handlerMenssage(ex, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    Map<String, String> handleValidationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach( error -> {

            String fieldName = error.getPropertyPath().toString();
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    Map<String, Object> torcedorException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return handlerMenssage(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> handlerMenssage(Exception ex, HttpStatus status) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("message", ex.getMessage());

        //Get all errors
        List<String> errors = Arrays.stream(ex.getStackTrace())
                .map(trace -> String.format(" %s.%s():%s", trace.getClassName(), trace.getMethodName(), trace.getLineNumber()))
                .collect(Collectors.toList());

        body.put("errors", errors);

        return body;


    }

}
