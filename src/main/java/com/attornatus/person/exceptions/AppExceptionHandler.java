package com.attornatus.person.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private MensagemRespostaException mensagemRespostaException;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<MensagemRespostaException> handleAllExceptions(Exception e,
                                                                               WebRequest request) {
        mensagemRespostaException = new MensagemRespostaException(
                new Date(),
                e.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(mensagemRespostaException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EnderecoInvalidoException.class)
    public final ResponseEntity<MensagemRespostaException> handleEnderecoInvalidoException(EnderecoInvalidoException e,
                                                                                           WebRequest request) {
        mensagemRespostaException = new MensagemRespostaException(
                new Date(),
                e.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(mensagemRespostaException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public final ResponseEntity<MensagemRespostaException> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
                                                                                                WebRequest request) {
        mensagemRespostaException = new MensagemRespostaException(
                new Date(),
                e.getMessage(),
                request.getDescription(false),
                HttpStatus.NO_CONTENT.value()
        );

        return new ResponseEntity<>(mensagemRespostaException, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(PessoaInvalidaException.class)
    public final ResponseEntity<MensagemRespostaException> handlePessoaInvalidaException(PessoaInvalidaException e,
                                                                                         WebRequest request) {
        mensagemRespostaException = new MensagemRespostaException(
                new Date(),
                e.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()
        );

        return new ResponseEntity<>(mensagemRespostaException, HttpStatus.BAD_REQUEST);
    }
}
