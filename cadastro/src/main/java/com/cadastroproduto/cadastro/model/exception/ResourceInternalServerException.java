package com.cadastroproduto.cadastro.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceInternalServerException extends RuntimeException {

    public ResourceInternalServerException(String mensagem) {
        super(mensagem);
    }
}

