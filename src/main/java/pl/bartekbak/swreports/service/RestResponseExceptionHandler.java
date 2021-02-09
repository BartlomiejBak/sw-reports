package pl.bartekbak.swreports.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.bartekbak.swreports.exception.InvalidQueryException;
import pl.bartekbak.swreports.exception.QueryProcessingException;
import pl.bartekbak.swreports.exception.ResourceNotFoundException;

@ControllerAdvice
public class RestResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(e.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidQueryException.class,
                QueryProcessingException.class})
    public ResponseEntity<Object> handleQueryException(Exception e) {
        return new ResponseEntity<>(e.getMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
