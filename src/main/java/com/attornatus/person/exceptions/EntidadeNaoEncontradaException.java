package com.attornatus.person.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntidadeNaoEncontradaException extends NoSuchElementException {
    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
