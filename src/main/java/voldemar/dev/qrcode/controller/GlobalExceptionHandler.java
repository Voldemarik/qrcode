package voldemar.dev.qrcode.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import voldemar.dev.qrcode.exception.AlreadyExistsException;
import voldemar.dev.qrcode.dto.output.ErrorResponse;
import voldemar.dev.qrcode.exception.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound (NotFoundException ex) {
        return ResponseEntity.status(404).body(new ErrorResponse(ex.getMessage()));
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest (AlreadyExistsException ex) {
        return ResponseEntity.status(400).body(new ErrorResponse(ex.getMessage()));
    }
}
