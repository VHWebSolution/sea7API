package com.cadastroproduto.cadastro.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.cadastroproduto.cadastro.model.error.ErrorMessage;
import com.cadastroproduto.cadastro.model.exception.ResourceBadRequestException;
import com.cadastroproduto.cadastro.model.exception.ResourceInternalServerException;
import com.cadastroproduto.cadastro.model.exception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestExceptionHandler {
    
    /**
     * Este método faz o tratamento do erro 404 Not Found
     * @param ex é o parametro que irá receber a mensagem que o o erro vai retornar
     * @return Retorna um ResponseEntity da mensagem de erro com o Http Not Found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorMessage error = new ErrorMessage("Not Found", HttpStatus.NOT_FOUND.value(),ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Este método faz o tratamento do erro 400 Bad Request
     * @param ex é o parametro que irá receber a mensagem que o o erro vai retornar
     * @return Retorna um ResponseEntity da mensagem de erro com o Http Bad Request
     */
    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<?> handleResourceBadRequestException(ResourceBadRequestException ex){
        ErrorMessage error = new ErrorMessage("Bad Request", HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

     /**
     * Este método faz o tratamento do erro 500 Internal Server Error
     * @param ex é o parametro que irá receber a mensagem que o o erro vai retornar
     * @return Retorna um ResponseEntity da mensagem de erro com o Http Internal Server Error
     */
    @ExceptionHandler(ResourceInternalServerException.class)
    public ResponseEntity<?> handleResourceInternalServerException(ResourceInternalServerException ex){
        ErrorMessage error = new ErrorMessage("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFoundError(HttpServletRequest request) {
        // Obtém o caminho da URL atual
        String requestUri = request.getRequestURI();

        // Retorna o erro 404 somente se a rota for localhost:8080
        if (isRootRequest(requestUri)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private boolean isRootRequest(String requestUri) {
        // Verifica se a rota acessada é localhost:8080
        return requestUri.equals("/") || requestUri.equals("/index.html");
    }
}