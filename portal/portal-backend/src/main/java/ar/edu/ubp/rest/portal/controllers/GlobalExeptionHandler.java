package ar.edu.ubp.rest.portal.controllers;

import java.sql.SQLException;

import org.springframework.dao.UncategorizedDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// No captura los errores a nivel de filtro, solo a nivel controladores (filtro->controller->handler)
@ControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handlerArgumentExeption(IllegalArgumentException exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handlerRuntimeExeption(RuntimeException exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(UncategorizedDataAccessException.class)
    public ResponseEntity<String> handleSQLException(UncategorizedDataAccessException exception) {
        if (exception.getRootCause() instanceof SQLException) {
            SQLException sqlException = (SQLException) exception.getRootCause();
            int errorCode = sqlException.getErrorCode();
            if (errorCode >= 50000) { 
                return new ResponseEntity<String>(sqlException.getMessage(), HttpStatus.CONFLICT);
            }
        }
        return new ResponseEntity<String>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
