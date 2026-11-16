package org.example.errorHandling;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandling {

    @ExceptionHandler(RuntimeException.class)
    public String handle(RuntimeException e){
        return e.getMessage();
    }
}
