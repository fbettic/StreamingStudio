package ar.edu.ubp.rest.plataformasrest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ar.edu.ubp.rest.plataformasrest.beans.ErrorBean;

// No captura los errores a nivel de filtro, solo a nivel controladores (filtro->controller->handler)
@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorBean> handleNumberFormatException(NumberFormatException ex) {
        return new ResponseEntity<>(new ErrorBean(ex.getMessage(),"Invalid number format"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorBean> handleAllExceptions(Exception ex) {
        return new ResponseEntity<>(new ErrorBean("Internal server error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
