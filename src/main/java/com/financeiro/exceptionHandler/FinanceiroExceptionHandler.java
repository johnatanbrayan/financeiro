package com.financeiro.exceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class FinanceiroExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
    HttpHeaders headers, HttpStatus status, WebRequest request) {
        
        String userMessage = messageSource.getMessage("invalid.message", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.getCause().toString();

        return handleExceptionInternal(ex, new Error(userMessage, developerMessage), headers, HttpStatus.BAD_REQUEST, request);
    }


    public static class Error {
        
        public String userMessage;
        public String developerMessage;

        public Error(String userMessage, String developerMessage) {
            this.userMessage = userMessage;
            this.developerMessage = developerMessage;
        }

        public String getUserMessage() { return this.userMessage; }

        public String getDeveloperMessage() { return this.developerMessage; }
    }
}